package cn.structure.starter.manager.mapper;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * <p>
 * JPA mapper
 * </p>
 *
 * @author chuck
 * @version 1.0.1
 * @since 2020/12/29 22:38
 */
public interface JpaMapper<T> extends JpaRepository<T, Object> {
}
