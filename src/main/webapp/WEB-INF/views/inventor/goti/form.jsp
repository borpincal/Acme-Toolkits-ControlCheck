<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox placeholder="000000:yy:mm:dd" code="inventor.list.label.chimpum.code" path="code"/>
	<acme:input-moment code="inventor.list.label.chimpum.creationMoment" path="creationTime" readonly="true"/>
	<acme:input-moment code="inventor.list.label.chimpum.startTime" path="startTime"/>
	<acme:input-moment code="inventor.list.label.chimpum.endTime" path="endTime"/>
	<acme:input-textbox placeholder="Lorem ipsum" code="inventor.list.label.chimpum.title" path="theme"/>
	<acme:input-textbox placeholder="Lorem ipsum" code="inventor.list.label.chimpum.description" path="summary"/>
	<acme:input-money code="inventor.list.label.chimpum.budget" path="quantity"/>
	<acme:input-textbox placeholder="http://www.example.com" code="inventor.list.label.chimpum.link" path="furtherInfo"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.chimpum.form.button.update" action="/inventor/goti/update?masterId=${masterId}"/>
			<acme:submit code="inventor.chimpum.form.button.delete" action="/inventor/goti/delete?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.chimpum.form.button.create" action="/inventor/goti/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>