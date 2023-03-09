package com.zepebackend.dao.read;

import com.zepebackend.dto.ForgotPwdRequestDto;
import com.zepebackend.dto.PartenariatDto;
import com.zepebackend.entity.ForgotPwd;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.data.repository.Repository;

import java.io.Serializable;
import java.util.List;
import java.util.Optional;

@NoRepositoryBean
public interface CoreAbstractQueryRepository<T, ID extends Serializable> extends Repository<T, ID> {
    List<T> findAll();    
    
    List<T> findAll(Sort sort);

    long count();

    List<PartenariatDto> findAllById(Iterable<ID> ids);

    Optional<T> findById(ID id);

    T getOne(ID id);

    boolean existsById(ID id);


    Page<T> findAll(Pageable pageable);

    long count(Specification<T> spec);

    List<T> findAll(Specification<T> spec);

    Page<T> findAll(Specification<T> spec, Pageable pageable);

    List<T> findAll(Specification<T> spec, Sort sort);

    Optional<T> findOne(Specification<T> spec);
    
    //List<T> findAllById(Iterable<ID> var1);

}
