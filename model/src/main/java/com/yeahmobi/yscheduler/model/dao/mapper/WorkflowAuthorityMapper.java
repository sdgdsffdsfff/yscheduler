package com.yeahmobi.yscheduler.model.dao.mapper;

import com.yeahmobi.yscheduler.model.WorkflowAuthority;
import com.yeahmobi.yscheduler.model.WorkflowAuthorityExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

public interface WorkflowAuthorityMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int countByExample(WorkflowAuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int deleteByExample(WorkflowAuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int insert(WorkflowAuthority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int insertSelective(WorkflowAuthority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    List<WorkflowAuthority> selectByExampleWithRowbounds(WorkflowAuthorityExample example, RowBounds rowBounds);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    List<WorkflowAuthority> selectByExample(WorkflowAuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    WorkflowAuthority selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int updateByExampleSelective(@Param("record") WorkflowAuthority record, @Param("example") WorkflowAuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int updateByExample(@Param("record") WorkflowAuthority record, @Param("example") WorkflowAuthorityExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int updateByPrimaryKeySelective(WorkflowAuthority record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table workflow_authority
     *
     * @mbggenerated
     */
    int updateByPrimaryKey(WorkflowAuthority record);
}