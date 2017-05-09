package com.yline.callback;

import java.util.Collection;
import java.util.List;

/**
 * adapter 需要支持的数据操作
 *
 * @author yline 2017/5/9 -- 15:15
 * @version 1.0.0
 */
public interface IDataAdapterCallback<E>
{
	/**
	 * 获取数据列表，最好数据集不可操作
	 *
	 * @return
	 */
	List<E> getDataList();

	/**
	 * 依据位置，获取相应的数据
	 *
	 * @param position
	 * @return
	 */
	E getItem(int position);

	/**
	 * 放置，全部的数据
	 *
	 * @param list
	 */
	void setDataList(List<E> list);

	/**
	 * 在数据末尾，添加一条数据
	 *
	 * @param object
	 * @return
	 */
	boolean add(E object);

	/**
	 * 在指定位置，添加一条数据
	 *
	 * @param index
	 * @param element
	 */
	void add(int index, E element);

	/**
	 * 在数据末尾，添加批量的数据
	 *
	 * @param collection
	 * @return
	 */
	boolean addAll(Collection<? extends E> collection);

	/**
	 * 清空数据
	 */
	void clear();

	/**
	 * 判断是否包含某一项数据
	 *
	 * @param object
	 * @return
	 */
	boolean contains(Object object);

	/**
	 * 判断是否包含某一批数据
	 *
	 * @param collection
	 * @return
	 */
	boolean containsAll(Collection<?> collection);

	/**
	 * 判断 数据列表是否有数据
	 *
	 * @return
	 */
	boolean isEmpty();

	/**
	 * 移除某一个位置上，的数据
	 *
	 * @param index
	 * @return
	 */
	E remove(int index);

	/**
	 * 移除，某一条数据
	 *
	 * @param object
	 * @return
	 */
	boolean remove(Object object);

	/**
	 * 移除，某一批数据
	 *
	 * @param collection
	 * @return
	 */
	boolean removeAll(Collection<?> collection);

	/**
	 * 获取数据量大小
	 *
	 * @return
	 */
	int size();
}
