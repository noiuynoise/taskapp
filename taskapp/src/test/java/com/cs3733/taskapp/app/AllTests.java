package com.cs3733.taskapp.app;

import org.junit.FixMethodOrder;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.junit.runners.MethodSorters;

@RunWith(Suite.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@SuiteClasses({ TestAddTaskHandler.class, TestAddTeammateHandler.class, TestArchiveProjectHandler.class,
		TestAssignTeammateHandler.class, TestCreateProjectHandler.class, TestDecomposeTaskHandler.class,
		TestDeleteProjectHandler.class, TestListProjectHandler.class, TestMarkTaskHandler.class,
		TestProjectViewHandler.class, TestRemoveTeammateHandler.class, TestRenameTaskHandler.class,
		TestTeamViewHandler.class, TestUnassignTeammateHandler.class })
public class AllTests {

}
