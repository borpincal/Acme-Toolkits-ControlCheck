<%@page language="java"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="acme" uri="urn:jsptagdir:/WEB-INF/tags"%>

<acme:form>
	<acme:input-textbox placeholder="ABC-123-A-yy-mm-dd" code="inventor.list.label.chimpum.code" path="code"/>
	<acme:input-moment code="inventor.list.label.chimpum.creationMoment" path="creationTime" readonly="true"/>
	<acme:input-moment code="inventor.list.label.chimpum.startTime" path="startTime"/>
	<acme:input-moment code="inventor.list.label.chimpum.endTime" path="endTime"/>
	<acme:input-textbox placeholder="Lorem ipsum" code="inventor.list.label.chimpum.title" path="title"/>
	<acme:input-textbox placeholder="Lorem ipsum" code="inventor.list.label.chimpum.description" path="description"/>
	<acme:input-money code="inventor.list.label.chimpum.budget" path="budget"/>
	<acme:input-textbox placeholder="http://www.example.com" code="inventor.list.label.chimpum.link" path="link"/>
	
	<jstl:choose>
		<jstl:when test="${acme:anyOf(command, 'show, update, delete')}">
			<acme:submit code="inventor.chimpum.form.button.update" action="/inventor/chimpum/update?masterId=${masterId}"/>
			<acme:submit code="inventor.chimpum.form.button.delete" action="/inventor/chimpum/delete?masterId=${masterId}"/>
		</jstl:when>
		<jstl:when test="${command == 'create'}">
			<acme:submit code="inventor.chimpum.form.button.create" action="/inventor/chimpum/create?masterId=${masterId}"/>
		</jstl:when>		
	</jstl:choose>		
</acme:form>