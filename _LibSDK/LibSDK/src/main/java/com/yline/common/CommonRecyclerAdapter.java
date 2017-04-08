package com.yline.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.Collection;
import java.util.List;

/**
 * @author yline 2017/3/19 -- 3:05
 * @version 1.0.0
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<CommonRecyclerViewHolder> implements ICommonAdapterCallback<T>
{
	protected List<T> sList;

	@Override
	public CommonRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new CommonRecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemRes(), parent, false));
	}

	@Override
	public abstract void onBindViewHolder(CommonRecyclerViewHolder viewHolder, int position);

	/**
	 * @return item 资源文件
	 */
	public abstract int getItemRes();

	@Override
	public int getItemCount()
	{
		return sList.size();
	}

	/**
	 * 返回某项数据
	 *
	 * @param position 位置
	 * @return 某项数据
	 */
	@Override
	public T getItem(int position)
	{
		if (position >= sList.size())
		{
			throw new IllegalArgumentException("invalid position");
		}
		return sList.get(position);
	}

	/**
	 * 获取全部数据
	 *
	 * @return 所有数据
	 */
	public List<T> getDataList()
	{
		return this.sList;
	}

	/**
	 * 完全更新数据
	 *
	 * @param list 所有数据
	 */
	public void setDataList(List<T> list)
	{
		this.sList = list;
		this.notifyDataSetChanged();
	}

	@Override
	public boolean add(T object)
	{
		boolean result = sList.add(object);
		this.notifyDataSetChanged();
		return result;
	}

	@Override
	public void add(int index, T element)
	{
		sList.add(index, element);
		this.notifyItemInserted(index);
	}

	@Override
	public boolean addAll(Collection collection)
	{
		boolean result = sList.addAll(collection);
		this.notifyDataSetChanged();
		return result;
	}

	@Override
	public void clear()
	{
		sList.clear();
		this.notifyDataSetChanged();
	}

	@Override
	public boolean contains(Object object)
	{
		boolean result = sList.contains(object);
		return result;
	}

	@Override
	public boolean isEmpty()
	{
		return false;
	}

	@Override
	public boolean remove(Object object)
	{
		boolean result = sList.remove(object);
		this.notifyDataSetChanged();
		return result;
	}

	@Override
	public T remove(int index)
	{
		T t = sList.remove(index);
		this.notifyItemRemoved(index);
		return t;
	}

	@Override
	public int size()
	{
		int size = sList.size();
		return size;
	}

	@Override
	public boolean removeAll(Collection collection)
	{
		boolean result = sList.removeAll(collection);
		this.notifyDataSetChanged();
		return result;
	}

	@Override
	public boolean containsAll(Collection collection)
	{
		boolean result = sList.containsAll(collection);
		return result;
	}
}
