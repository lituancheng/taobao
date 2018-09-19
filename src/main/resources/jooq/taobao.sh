#!/bin/sh
java  -classpath jooq-3.10.2.jar:jooq-meta-3.10.2.jar:jooq-codegen-3.10.2.jar:mysql-connector-java-6.0.6.jar:. org.jooq.util.GenerationTool ./taobao.xml