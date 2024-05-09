package dao;

import java.util.List;

import entity.KhoaHoc;

public abstract class EduSysDAO<EntityType, KeyType> {
	// abstract là hàm chỉ khai báo không có phần thân
	public abstract void insert(EntityType entity);
	
	public abstract void update(EntityType entity);
	
	public abstract void delete(KeyType id);
	
	public abstract List<EntityType> selectAll();
	
	public abstract EntityType selectById(KeyType id);
	
	public abstract List<EntityType> selectBySql(String sql, Object... args);

}