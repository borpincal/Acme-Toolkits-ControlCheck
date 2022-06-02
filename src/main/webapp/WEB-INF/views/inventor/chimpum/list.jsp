<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:list>
	<acme:list-column code="inventor.list.label.chimpum.code" path="code"/>
	<acme:list-column code="inventor.list.label.chimpum.title" path="title"/>
	<acme:list-column code="inventor.list.label.chimpum.description" path="description"/>
</acme:list>
