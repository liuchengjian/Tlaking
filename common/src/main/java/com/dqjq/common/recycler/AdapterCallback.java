package com.dqjq.common.recycler;

/**
 */
public interface AdapterCallback<Data> {
    void update(Data data, BaseRecyclerAdapter.ViewHolder<Data> holder);
}
