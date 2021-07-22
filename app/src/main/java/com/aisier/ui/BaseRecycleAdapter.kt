package com.aisier

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.extensions.LayoutContainer

/**
 * <pre>
 *     @author : wutao
 *     e-mail : 670831931@qq.com
 *     time   : 2020/06/09
 *     desc   :
 *     version: 1.0
 * </pre>
 */
open class BaseRecyclerAdapter<M>(@LayoutRes val itemLayoutId: Int) :
        RecyclerView.Adapter<BaseRecyclerAdapter.CommonViewHolder>() {

    constructor(@LayoutRes itemLayoutId: Int, dataList: MutableList<M>?) : this(itemLayoutId) {
        this.dataList = dataList
    }

    constructor(@LayoutRes itemLayoutId: Int, onBindData: (itemView: View, position: Int) -> Unit) : this(itemLayoutId) {
        this.onBindData = onBindData
    }

    constructor(@LayoutRes itemLayoutId: Int, dataList: MutableList<M>?, onBindData: (itemView: View, position: Int) -> Unit) : this(itemLayoutId) {
        this.dataList = dataList
        this.onBindData = onBindData
    }


    var onItemClickListener: ((model: M?) -> Unit)? = null
    var onItemLongClickListener: ((v: View, position: Int) -> Boolean) = { _, _ -> false }
    var dataList: MutableList<M>? = null

    private var onBindData: ((itemView: View, position: Int) -> Unit)? = null

    protected var mContext: Context? = null

    fun onBindViewHolder(onBindData: ((itemView: View, position: Int) -> Unit)) {
        this.onBindData = onBindData
    }


    fun setData(list: Collection<M>?): Boolean {
        var result = false
        if (dataList == null) {
            dataList = mutableListOf()
        }
        dataList?.let {
            it.clear()
            if (list != null) {
                result = it.addAll(list)
                notifyDataSetChanged()
            }
        }
        return result
    }


    fun getItem(position: Int): M? = dataList?.let { it[position] }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CommonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(itemLayoutId, parent, false)
        val viewHolder = CommonViewHolder(itemView)
        mContext = parent.context
        itemView.setOnClickListener { onItemClickListener?.invoke(getItem(viewHolder.adapterPosition)) }
        return viewHolder
    }

    override fun getItemCount() = dataList?.size ?: 0

    override fun onBindViewHolder(holder: CommonViewHolder, position: Int) {
        if (onBindData != null) {
            onBindData?.invoke(holder.itemView, position)
        } else {
            getItem(position)?.let {
                bindData(holder.itemView, it)
            }
        }
    }

    open fun bindData(itemView: View, model: M) = Unit

    class CommonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), LayoutContainer {

        override val containerView: View = itemView
    }

}
