package com.yline.view.common;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.yline.view.callback.IDataAdapterCallback;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 * @author yline 2017/3/19 -- 3:05
 * @version 1.0.0
 */
public abstract class CommonRecyclerAdapter<T> extends RecyclerView.Adapter<RecyclerViewHolder> implements IDataAdapterCallback<T>
{
	protected List<T> sList;

	public CommonRecyclerAdapter()
	{
		this.sList = new ArrayList<>();
	}

	@Override
	public RecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
	{
		return new RecyclerViewHolder(LayoutInflater.from(parent.getContext()).inflate(getItemRes(), parent, false));
	}

	@Override
	public abstract void onBindViewHolder(RecyclerViewHolder viewHolder, int position);

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

	@Override
	public List<T> getDataList()
	{
		return Collections.unmodifiableList(sList);
	}

	@Override
	public void setDataList(List<T> tList)
	{
		this.sList = new ArrayList<>(tList);
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
