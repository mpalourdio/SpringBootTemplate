project {
  modelVersion '4.0.0'
  parent {
    groupId 'org.springframework.boot'
    artifactId 'spring-boot-starter-parent'
    version '1.4.2.RELEASE'
  }
  groupId 'com.mpalourdio.projects'
  artifactId 'springboottemplate'
  version '1.0-SNAPSHOT'
  name 'Spring Boot Template'
  url 'http://maven.apache.org'
  scm {
    developerConnection 'scm:git:git@github.com:mpalourdio/SpringBootTemplate.git'
  }
  properties {
    'java.version' '1.8'
    'project.build.sourceEncoding' 'UTF-8'
  }
  dependencies {
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-web'
    }
    dependency {
      groupId 'commons-codec'
      artifactId 'commons-codec'
      version '1.10'
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-thymeleaf'
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-data-jpa'
    }
    dependency {
      groupId 'mysql'
      artifactId 'mysql-connector-java'
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-test'
      scope 'test'
    }
    dependency {
      groupId 'org.springframework.boot'
      artifactId 'spring-boot-starter-security'
    }
    dependency {
      groupId 'org.hibernate'
      artifactId 'hibernate-java8'
    }
    dependency {
      groupId 'org.apache.commons'
      artifactId 'commons-lang3'
      version '3.4'
    }
    dependency {
      groupId 'com.h2database'
      artifactId 'h2'
      scope 'test'
    }
  }
  build {
    pluginManagement {
      plugins {
        plugin {
          artifactId 'maven-release-plugin'
          version '2.5.3'
        }
      }
    }
    plugins {
      plugin {
        groupId 'org.springframework.boot'
        artifactId 'spring-boot-maven-plugin'
      }
      plugin {
        artifactId 'maven-release-plugin'
        configuration {
          pushChanges 'false'
          arguments '-Dmaven.javadoc.skip=true'
        }
      }
      plugin {
        artifactId 'maven-surefire-plugin'
        version '2.19.1'
        configuration {
          includes {
            include '**/*Test.java'
          }
        }
      }
    }
  }
}
