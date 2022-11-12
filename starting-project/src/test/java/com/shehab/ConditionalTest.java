package com.shehab;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledForJreRange;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;
import org.junit.jupiter.api.condition.EnabledIfSystemProperty;
import org.junit.jupiter.api.condition.EnabledOnJre;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.JRE;
import org.junit.jupiter.api.condition.OS;

class ConditionalTest {

	@Test
	@EnabledOnOs(OS.LINUX)
	void testOnlyForLinux() {
		
	}
	
	@Test
	@EnabledOnJre(JRE.JAVA_17)
	void testOnlyForJava17() {
		
	}
	@Test
	@EnabledForJreRange(min=JRE.JAVA_12,max = JRE.JAVA_18)
	void testOnlyForJavaRange() {
		
	}
	
	@Test
	@Disabled
	void testDisabled() {
		
	}
	
	@Test
	@EnabledIfSystemProperty(named = "name",matches = "shehab")
	void testOnlyForSystemProperty() {
		
	}
	
	@Test
	@EnabledIfEnvironmentVariable(named = "name",matches = "ahmed")
	void testOnlyForEnvironmentProperty() {
		
	}
}
