package lchj.tlaking;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.AnticipateOvershootInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.ViewTarget;
import com.dqjq.common.BaseActivty;
import com.dqjq.common.widget.PortraitView;
import com.dqjq.factory.utils.Account;

import net.qiujuer.genius.ui.Ui;
import net.qiujuer.genius.ui.widget.FloatActionButton;

import java.util.Objects;

import butterknife.BindView;
import butterknife.OnClick;
import lchj.tlaking.activity.SearchActivity;
import lchj.tlaking.fragment.ActiveFragment;
import lchj.tlaking.fragment.ContactFragment;
import lchj.tlaking.fragment.GroupFragment;
import lchj.tlaking.fragment.MyFragment;
import lchj.tlaking.helper.BottomNavigationViewHelper;
import lchj.tlaking.helper.NavHelper;

public class MainActivity extends BaseActivty implements BottomNavigationView.OnNavigationItemSelectedListener,
        NavHelper.OnTabChangedListener<Integer> {

    private Fragment mCurFragment;
    @BindView(R.id.navigation)
    BottomNavigationView mNavigation;

    @BindView(R.id.appbar)
    View mLayAppbar;

    @BindView(R.id.im_portrait)
    PortraitView mPortrait;

    @BindView(R.id.txt_title)
    TextView mTitle;

    @BindView(R.id.lay_container)
    FrameLayout mContainer;

    @BindView(R.id.btn_action)
    FloatActionButton mAction;

    @BindView(R.id.im_searchs)
    ImageView mSearch;

    private NavHelper<Integer> mNavHelper;

    /**
     * MainActivity 显示的入口
     *
     * @param context 上下文
     */
    public static void show(Context context) {
        context.startActivity(new Intent(context, MainActivity.class));
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.activity_main;
    }

    @OnClick(R.id.im_searchs)
    void searchClick() {
        int type = Objects.equals(mNavHelper.getCurrentTab().extra, R.string.action_group) ? SearchActivity.TYPE_GROUP : SearchActivity.TYPE_USER;

        startAct(SearchActivity.class, type);
    }

    @OnClick(R.id.im_portrait)
    void portraitClick() {
        startAct(UserActivity.class);
    }

    /**
     * 浮动按钮点击
     */
    @OnClick(R.id.btn_action)
    void actionClick() {

        if (Objects.equals(mNavHelper.getCurrentTab().extra, R.string.action_group)) {
            //TODO:打开群的界面

        } else {

        }
    }


    @Override
    protected void initWidget() {
        super.initWidget();
        mCurFragment = new MyFragment();
        //解决BottomNavigation四个以上item动画问题
        BottomNavigationViewHelper.disableShiftMode(mNavigation);
        // 初始化底部辅助工具类
        mNavHelper = new NavHelper<>(this, R.id.lay_container,
                getSupportFragmentManager(), MainActivity.this);
        mNavHelper.add(R.id.action_home, new NavHelper.Tab<>(ActiveFragment.class, R.string.action_home))
                .add(R.id.action_group, new NavHelper.Tab<>(GroupFragment.class, R.string.action_group))
                .add(R.id.action_contact, new NavHelper.Tab<>(ContactFragment.class, R.string.action_contact))
                .add(R.id.action_my, new NavHelper.Tab<>(MyFragment.class, R.string.action_my));


        // 添加对底部按钮点击的监听
        mNavigation.setOnNavigationItemSelectedListener(this);

        Glide.with(this)
                .load(R.drawable.bg_src_morning)
                .centerCrop()
                .into(new ViewTarget<View, GlideDrawable>(mLayAppbar) {
                    @Override
                    public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
                        this.view.setBackground(resource.getCurrent());
                    }
                });
    }
    @Override
    protected boolean initArgs(Bundle bundle) {
        if (Account.isComplete()) {
            // 判断用户信息是否完全，完全则走正常流程
            return super.initArgs(bundle);
        } else {
            startAct(UserActivity.class);
            return false;
        }
    }

    @Override
    protected void initData() {
        super.initData();
        // 从底部导中接管我们的Menu，然后进行手动的触发第一次点击
        Menu menu = mNavigation.getMenu();
        // 触发首次选中Home
        menu.performIdentifierAction(R.id.action_home, 0);

        // 初始化头像加载
        mPortrait.setup(Glide.with(this), Account.getUser());
    }

    /**
     * 当我们的底部导航被点击的时候触发
     *
     * @param item MenuItem
     * @return True 代表我们能够处理这个点击
     */
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // 转接事件流到工具类中
        return mNavHelper.performClickMenu(item.getItemId());
    }

    /**
     * NavHelper 处理后回调的方法
     *
     * @param newTab 新的Tab
     * @param oldTab 就的Tab
     */
    @Override
    public void onTabChanged(NavHelper.Tab<Integer> newTab, NavHelper.Tab<Integer> oldTab) {
        // 从额外字段中取出我们的Title资源Id
        mTitle.setText(newTab.extra);

        // 对浮动按钮进行隐藏与显示的动画
        float transY = 0;
        float rotation = 0;
        if (Objects.equals(newTab.extra, R.string.action_home)) {
            mSearch.setVisibility(View.VISIBLE);
            // 主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        }else if(Objects.equals(newTab.extra, R.string.action_my)){
            mSearch.setVisibility(View.GONE);
            // 主界面时隐藏
            transY = Ui.dipToPx(getResources(), 76);
        } else {
            // transY 默认为0 则显示
            if (Objects.equals(newTab.extra, R.string.action_group)) {
                // 群
                mSearch.setVisibility(View.VISIBLE);
                mAction.setImageResource(R.drawable.ic_group_add);
                rotation = -360;
            } else {
                mSearch.setVisibility(View.GONE);
                // 联系人
                mAction.setImageResource(R.drawable.ic_contact_add);
                rotation = 360;
            }
        }

        // 开始动画
        // 旋转，Y轴位移，弹性差值器，时间
        mAction.animate()
                .rotation(rotation)
                .translationY(transY)
                .setInterpolator(new AnticipateOvershootInterpolator(1))
                .setDuration(480)
                .start();

    }


    // Activity中收到剪切图片成功的回调
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        mCurFragment.onActivityResult(requestCode, resultCode, data);
    }
}
