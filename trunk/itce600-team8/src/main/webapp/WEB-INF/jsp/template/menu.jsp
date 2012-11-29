<%@ page contentType="text/html; charset=UTF-8" %>
<%@ include file="../template/includes.jsp" %>

<div id="news" class="boxed1">
	<h1 class="title"><s:text name="label.menu"/></h1>
	<div class="content">
		<ul>
              	<li>
				<p><strong><a href="<c:url value="/admin/addCreatorInput.task"/>"><s:text name="admin.creator.add"/></a></strong></p>
                  </li>
                  <li>
				<p><strong><a href="<c:url value="/admin/viewCreators.task"/>"><s:text name="admin.creators.view"/></a></strong></p>
                  </li>
                  <li>
				<p><strong><a href="<c:url value="/admin/viewConsumers.task"/>"><s:text name="admin.consumers.view"/></a></strong></p>
                  </li>
		</ul>
	</div>
</div>

