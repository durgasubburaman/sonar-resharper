/*
 * SonarQube ReSharper Plugin
 * Copyright (C) 2014 SonarSource
 * dev@sonar.codehaus.org
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.plugins.resharper;

import com.google.common.collect.ImmutableList;
import org.sonar.api.PropertyType;
import org.sonar.api.SonarPlugin;
import org.sonar.api.config.PropertyDefinition;
import org.sonar.api.resources.Qualifiers;

import java.util.List;

public class ReSharperPlugin extends SonarPlugin {

  public static final String PROJECT_NAME_PROPERTY_KEY = "sonar.resharper.projectName";
  public static final String SOLUTION_FILE_PROPERTY_KEY = "sonar.resharper.solutionFile";
  public static final String INSPECTCODE_PATH_PROPERTY_KEY = "sonar.resharper.inspectcode.path";
  public static final String TIMEOUT_MINUTES_PROPERTY_KEY = "sonar.resharper.timeoutMinutes";
  public static final String INSTALL_DIRECTORY_KEY = "sonar.resharper.installDirectory";
  public static final String PROPERTY_CATEGORY = "ReSharper";

  /**
   * {@inheritDoc}
   */
  @Override
  public List getExtensions() {
    ImmutableList.Builder builder = ImmutableList.builder();

    builder.addAll(CSharpReSharperProvider.extensions());
    builder.addAll(VBNetReSharperProvider.extensions());

    builder.addAll(pluginProperties());

    return builder.build();
  }

  private static ImmutableList<PropertyDefinition> pluginProperties() {
    return ImmutableList.of(
      PropertyDefinition.builder(PROJECT_NAME_PROPERTY_KEY)
        .name("Visual Studio project name")
        .description("Example: MyLibrary")
        .category(PROPERTY_CATEGORY)
        .onlyOnQualifiers(Qualifiers.MODULE)
        .build(),

      PropertyDefinition.builder(SOLUTION_FILE_PROPERTY_KEY)
        .name("Solution file")
        .description("Example: C:/Projects/MyProject/MySolution.sln")
        .category(PROPERTY_CATEGORY)
        .onlyOnQualifiers(Qualifiers.MODULE)
        .build(),

      PropertyDefinition.builder(INSPECTCODE_PATH_PROPERTY_KEY)
        .name("Path to inspectcode.exe")
        .description("Example: C:/Program Files/jb-commandline-8.1.23.523/inspectcode.exe")
        .category(PROPERTY_CATEGORY)
        .onQualifiers(Qualifiers.PROJECT)
        .deprecatedKey(INSTALL_DIRECTORY_KEY)
        .build(),

      PropertyDefinition.builder(TIMEOUT_MINUTES_PROPERTY_KEY)
        .name("ReSharper execution timeout")
        .description("Time in minutes after which ReSharper's execution should be interrupted if not finished")
        .defaultValue("60")
        .category(PROPERTY_CATEGORY)
        .onQualifiers(Qualifiers.PROJECT)
        .type(PropertyType.INTEGER)
        .build(),

      PropertyDefinition.builder(INSTALL_DIRECTORY_KEY)
        .name("ReSharper Command Line Tools install directory")
        .description("Absolute path of the ReSharper Command Line Tools installation folder.")
        .category(PROPERTY_CATEGORY)
        .hidden()
        .build());
  }
}
