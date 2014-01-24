/**
 * Copyright (c) 2000-2013 Liferay, Inc. All rights reserved.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 */

package com.liferay.sync.engine;

import com.liferay.sync.engine.upgrade.UpgradeProcessSuite;
import com.liferay.sync.engine.util.HttpUtil;
import com.liferay.sync.engine.util.PropsKeys;
import com.liferay.sync.engine.util.PropsUtil;

import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import org.junit.Before;

import org.mockito.Mockito;

import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;

/**
 * @author Shinn Lok
 */
@PowerMockIgnore("javax.crypto.*")
@PrepareForTest(HttpUtil.class)
public abstract class BaseTestCase {

	@Before
	public void setUp() throws Exception {
		PropsUtil.set(PropsKeys.SYNC_DATABASE_NAME, "liferay-sync-test");

		UpgradeProcessSuite upgradeProcessSuite = new UpgradeProcessSuite();

		upgradeProcessSuite.upgrade();
	}

	protected void setMockPostResponse(String fileName) throws Exception {
		PowerMockito.mockStatic(HttpUtil.class);

		Class<?> clazz = getClass();

		InputStream inputStream = clazz.getResourceAsStream(fileName);

		String response = IOUtils.toString(inputStream);

		inputStream.close();

		Mockito.when(
			HttpUtil.executePost(
				Mockito.anyLong(), Mockito.anyString(), Mockito.anyMap())
		).thenReturn(
				response
		);
	}

}