/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.dao.mapper;

import org.apache.dolphinscheduler.common.enums.FailureStrategy;
import org.apache.dolphinscheduler.common.enums.ReleaseState;
import org.apache.dolphinscheduler.common.enums.WarningType;
import org.apache.dolphinscheduler.dao.BaseDaoTest;
import org.apache.dolphinscheduler.dao.entity.Project;
import org.apache.dolphinscheduler.dao.entity.Schedule;
import org.apache.dolphinscheduler.dao.entity.User;
import org.apache.dolphinscheduler.dao.entity.WorkflowDefinition;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

public class ScheduleMapperTest extends BaseDaoTest {

    @Autowired
    private ScheduleMapper scheduleMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private ProjectMapper projectMapper;

    @Autowired
    private WorkflowDefinitionMapper workflowDefinitionMapper;

    /**
     * insert
     * @return Schedule
     */
    private Schedule insertOne() {
        // insertOne
        Schedule schedule = new Schedule();
        schedule.setStartTime(new Date());
        schedule.setEndTime(new Date());
        schedule.setCrontab("");
        schedule.setFailureStrategy(FailureStrategy.CONTINUE);
        schedule.setReleaseState(ReleaseState.OFFLINE);
        schedule.setWarningType(WarningType.NONE);
        schedule.setCreateTime(new Date());
        schedule.setUpdateTime(new Date());
        scheduleMapper.insert(schedule);
        return schedule;
    }

    /**
     * test update
     */
    @Test
    public void testUpdate() {
        // insertOne
        Schedule schedule = insertOne();
        schedule.setCreateTime(new Date());
        // update
        int update = scheduleMapper.updateById(schedule);
        Assertions.assertEquals(update, 1);
    }

    /**
     * test delete
     */
    @Test
    public void testDelete() {
        Schedule schedule = insertOne();
        int delete = scheduleMapper.deleteById(schedule.getId());
        Assertions.assertEquals(delete, 1);
    }

    /**
     * test query
     */
    @Test
    public void testQuery() {
        Schedule schedule = insertOne();
        // query
        List<Schedule> schedules = scheduleMapper.selectList(null);
        Assertions.assertNotEquals(0, schedules.size());
    }

    /**
     * test page
     */
    @Test
    public void testQueryByProcessDefineIdPaging() {

        User user = new User();
        user.setUserName("ut name");
        userMapper.insert(user);

        Project project = new Project();
        project.setName("ut project");
        project.setUserId(user.getId());
        project.setCode(1L);
        project.setUpdateTime(new Date());
        project.setCreateTime(new Date());
        projectMapper.insert(project);

        WorkflowDefinition workflowDefinition = new WorkflowDefinition();
        workflowDefinition.setCode(1L);
        workflowDefinition.setProjectCode(project.getCode());
        workflowDefinition.setUserId(user.getId());
        workflowDefinition.setLocations("");
        workflowDefinition.setCreateTime(new Date());
        workflowDefinition.setUpdateTime(new Date());
        workflowDefinitionMapper.insert(workflowDefinition);

        Schedule schedule = insertOne();
        schedule.setUserId(user.getId());
        schedule.setWorkflowDefinitionCode(workflowDefinition.getCode());
        scheduleMapper.updateById(schedule);

        Page<Schedule> page = new Page(1, 3);
        IPage<Schedule> scheduleIPage = scheduleMapper.queryByWorkflowDefinitionCodePaging(page,
                workflowDefinition.getCode(), "");
        Assertions.assertNotEquals(0, scheduleIPage.getSize());
    }

    /**
     * test page
     */
    @Test
    public void testQueryByProjectAndProcessDefineIdPaging() {

        User user = new User();
        user.setUserName("ut name");
        userMapper.insert(user);

        Project project = new Project();
        project.setName("ut project");
        project.setUserId(user.getId());
        project.setCode(1L);
        project.setUpdateTime(new Date());
        project.setCreateTime(new Date());
        projectMapper.insert(project);

        WorkflowDefinition workflowDefinition = new WorkflowDefinition();
        workflowDefinition.setCode(1L);
        workflowDefinition.setProjectCode(project.getCode());
        workflowDefinition.setUserId(user.getId());
        workflowDefinition.setLocations("");
        workflowDefinition.setCreateTime(new Date());
        workflowDefinition.setUpdateTime(new Date());
        workflowDefinitionMapper.insert(workflowDefinition);

        Schedule schedule = insertOne();
        schedule.setUserId(user.getId());
        schedule.setWorkflowDefinitionCode(workflowDefinition.getCode());
        scheduleMapper.updateById(schedule);

        Page<Schedule> page = new Page(1, 3);
        IPage<Schedule> scheduleIPage =
                scheduleMapper.queryByProjectAndWorkflowDefinitionCodePaging(page, project.getCode(),
                        workflowDefinition.getCode(), "");
        Assertions.assertNotEquals(0, scheduleIPage.getSize());
    }

    /**
     * test query schedule list by project name
     */
    @Test
    public void testQuerySchedulerListByProjectName() {

        User user = new User();
        user.setUserName("ut name");
        userMapper.insert(user);

        Project project = new Project();
        project.setName("ut project");
        project.setUserId(user.getId());
        project.setCode(1L);
        project.setUpdateTime(new Date());
        project.setCreateTime(new Date());
        projectMapper.insert(project);

        WorkflowDefinition workflowDefinition = new WorkflowDefinition();
        workflowDefinition.setCode(1L);
        workflowDefinition.setProjectCode(project.getCode());
        workflowDefinition.setUserId(user.getId());
        workflowDefinition.setLocations("");
        workflowDefinition.setCreateTime(new Date());
        workflowDefinition.setUpdateTime(new Date());
        workflowDefinitionMapper.insert(workflowDefinition);

        Schedule schedule = insertOne();
        schedule.setUserId(user.getId());
        schedule.setWorkflowDefinitionCode(workflowDefinition.getCode());
        scheduleMapper.updateById(schedule);

        Page<Schedule> page = new Page(1, 3);
        List<Schedule> schedules = scheduleMapper.querySchedulerListByProjectName(
                project.getName());

        Assertions.assertNotEquals(0, schedules.size());
    }

    /**
     * test query by process definition ids
     */
    @Test
    public void testSelectAllByWorkflowDefinitionArray() {

        Schedule schedule = insertOne();
        schedule.setWorkflowDefinitionCode(12345);
        schedule.setReleaseState(ReleaseState.ONLINE);
        scheduleMapper.updateById(schedule);

        List<Schedule> schedules =
                scheduleMapper.selectAllByWorkflowDefinitionArray(new long[]{schedule.getWorkflowDefinitionCode()});
        Assertions.assertNotEquals(0, schedules.size());
    }

    /**
     * test query by process definition id
     */
    @Test
    public void queryByWorkflowDefinitionCode() {
        Schedule schedule = insertOne();
        schedule.setWorkflowDefinitionCode(12345);
        scheduleMapper.updateById(schedule);

        Schedule schedules = scheduleMapper.queryByWorkflowDefinitionCode(schedule.getWorkflowDefinitionCode());
        Assertions.assertNotNull(schedules);
    }
}
