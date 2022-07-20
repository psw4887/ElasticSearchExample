package com.copark.elasticsearchexample.repository.jpa;

import com.copark.elasticsearchexample.entity.jpa.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}
