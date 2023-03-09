package com.zepebackend.dao.write;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
@NoRepositoryBean
public interface CoreAbstractCMDRepository<T,ID extends Serializable> extends Repository<T,ID> {

   T save(T entity); List<T> saveAll(Iterable<? extends T> entities);
   void flush();
   T saveAndFlush (T entity);
   void delete(T entity);
   void deleteAll(Iterable<? extends T> entities);
   void deleteById(ID id);
} 
 
