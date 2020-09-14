<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<fmt:setBundle basename="prop.internalization.jsp.header" var="headerBundle"/>
<fmt:setLocale value="${language}" scope="session"/>

<div id="app">
    <nav class="navbar">
        <div class="container is-fluid">
            <div class="navbar-brand">
                <a class="navbar-item" href="/">
                    <img class="image" src="${pageContext.request.contextPath}/pictures/logo.png"/>
                    BelUniversity
                </a>
            </div>

            <c:if test="${user==null}">
            <div class="navbar-menu">
                <div class="navbar-end">
                    <div class="navbar-item">
                        <div class="buttons">
                            <a href="${pageContext.request.contextPath}/controller?command=login_get"
                               class="button is-primary">
                                <strong><fmt:message key="login" bundle="${headerBundle}"/>
                                </strong>
                            </a>

                        </div>
                    </div>
                </div>
                </c:if>

                <c:if test="${user!=null}">
                <div class="navbar-menu">
                    <div class="navbar-end">
                        <div class="navbar-item">
                            <div class="buttons">
                                <a href="${pageContext.request.contextPath}/controller?command=logout"
                                   class="button is-primary">
                                    <strong><fmt:message key="logout" bundle="${headerBundle}"/></strong>
                                </a>
                            </div>
                        </div>
                    </div>
                    </c:if>


                </div>
    </nav>

    <nav class="navbar is-primary">
        <div class="container">
            <div class="navbar-menu">
                <div class="navbar-start">
                    <a href="${pageContext.request.contextPath}/controller?command=main_get"
                       class="navbar-item"><fmt:message key="main" bundle="${headerBundle}"/>
                    </a>

                    <div class="navbar-item has-dropdown is-hoverable">
                        <a class="navbar-link">
                            <fmt:message key="faculties" bundle="${headerBundle}"/>
                        </a>
                        <div class="navbar-dropdown">
                            <a href="${pageContext.request.contextPath}/controller?command=view_faculties_get"
                               class="navbar-item"><fmt:message key="view"
                                                                bundle="${headerBundle}"/>
                            </a>
                        </div>
                    </div>

                    <c:if test="${user!=null}">
                        <c:if test="${user.role.toString() == 'USER'}">
                            <c:if test="${entrant!=null}">
                                <div class="navbar-item has-dropdown is-hoverable">
                                    <a class="navbar-link">
                                        <fmt:message key="priorities" bundle="${headerBundle}"/>
                                    </a>
                                    <div class="navbar-dropdown">
                                        <c:if test="${entrant.priorities.size()<maxNumberOfPriorities}">
                                            <a href="${pageContext.request.contextPath}/controller?command=enter_priority_faculties_get"
                                               class="navbar-item"><fmt:message key="enter"
                                                                                bundle="${headerBundle}"/>
                                            </a>
                                        </c:if>
                                        <c:if test="${entrant.priorities!=null&&!entrant.priorities.isEmpty()}">
                                            <a href="${pageContext.request.contextPath}/controller?command=view_priority_faculties_get"
                                               class="navbar-item"><fmt:message key="view"
                                                                                bundle="${headerBundle}"/>
                                            </a>
                                            <a href="${pageContext.request.contextPath}/controller?command=delete_priority_faculties_get"
                                               class="navbar-item"><fmt:message key="delete"
                                                                                bundle="${headerBundle}"/>
                                            </a>
                                        </c:if>
                                    </div>
                                </div>
                            </c:if>
                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link">
                                    <fmt:message key="certificate" bundle="${headerBundle}"/>
                                </a>

                                <c:if test="${entrant==null}">
                                    <div class="navbar-dropdown">
                                        <a href="${pageContext.request.contextPath}/controller?command=enter_certificates_get"
                                           class="navbar-item"><fmt:message key="enter"
                                                                            bundle="${headerBundle}"/>
                                        </a>
                                    </div>
                                </c:if>


                                <c:if test="${entrant!=null}">
                                    <div class="navbar-dropdown">
                                        <c:if test="${entrant.results.size()<3}">
                                            <a href="${pageContext.request.contextPath}/controller?command=enter_certificates_get"
                                               class="navbar-item"><fmt:message key="enter"
                                                                                bundle="${headerBundle}"/>
                                            </a>
                                        </c:if>
                                        <a href="${pageContext.request.contextPath}/controller?command=view_certificates_get"
                                           class="navbar-item"><fmt:message
                                                key="view"
                                                bundle="${headerBundle}"/>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/controller?command=update_certificates_get"
                                           class="navbar-item"><fmt:message
                                                key="update"
                                                bundle="${headerBundle}"/>
                                        </a>
                                        <c:if test="${!entrant.results.isEmpty()}">

                                            <a href="${pageContext.request.contextPath}/controller?command=delete_certificates_get"
                                               class="navbar-item"><fmt:message
                                                    key="delete"
                                                    bundle="${headerBundle}"/>
                                            </a>
                                        </c:if>

                                    </div>
                                </c:if>
                            </div>

                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link">
                                    <fmt:message key="photo" bundle="${headerBundle}"/>
                                </a>
                                <div class="navbar-dropdown">
                                    <c:if test="${user.photos.size()<maxNumberPhotos}">
                                        <a href="${pageContext.request.contextPath}/controller?command=upload_photo_get"
                                           class="navbar-item"><fmt:message key="add"
                                                                            bundle="${headerBundle}"/>
                                        </a>
                                    </c:if>
                                    <c:if test="${!user.photos.isEmpty()}">
                                        <a href="${pageContext.request.contextPath}/controller?command=view_photos_get"
                                           class="navbar-item"><fmt:message key="view"
                                                                            bundle="${headerBundle}"/>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/controller?command=delete_photos_get"
                                           class="navbar-item"><fmt:message key="delete"
                                                                            bundle="${headerBundle}"/>
                                        </a>
                                    </c:if>
                                </div>
                            </div>
                        </c:if>

                        <c:if test="${user.role.toString() == 'ADMIN'}">
                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link">
                                    <fmt:message key="entrants" bundle="${headerBundle}"/>
                                </a>
                                <div class="navbar-dropdown">
                                    <a href="${pageContext.request.contextPath}/controller?command=admin_view_entrants_get"
                                       class="navbar-item"><fmt:message key="view"
                                                                        bundle="${headerBundle}"/>
                                    </a>
                                </div>
                            </div>
                            <div class="navbar-item has-dropdown is-hoverable">
                                <a class="navbar-link">
                                    <fmt:message key="news" bundle="${headerBundle}"/>
                                </a>
                                <div class="navbar-dropdown">
                                    <a href="${pageContext.request.contextPath}/controller?command=admin_view_all_news_get"
                                       class="navbar-item"><fmt:message key="view"
                                                                        bundle="${headerBundle}"/>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?command=admin_add_news_get"
                                       class="navbar-item"><fmt:message key="add"
                                                                        bundle="${headerBundle}"/>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?command=admin_update_all_news_get"
                                       class="navbar-item"><fmt:message key="update"
                                                                        bundle="${headerBundle}"/>
                                    </a>
                                    <a href="${pageContext.request.contextPath}/controller?command=admin_delete_all_news_get"
                                       class="navbar-item"><fmt:message key="delete"
                                                                        bundle="${headerBundle}"/>
                                    </a>
                                </div>
                            </div>
                        </c:if>

                        <div class="navbar-item has-dropdown is-hoverable">
                            <a class="navbar-link">
                                <fmt:message key="setting" bundle="${headerBundle}"/>
                            </a>
                            <div class="navbar-dropdown">
                                <a href="${pageContext.request.contextPath}/controller?command=update_profile_get"
                                   class="navbar-item"><fmt:message key="update"
                                                                    bundle="${headerBundle}"/>
                                </a>
                                <a href="${pageContext.request.contextPath}/controller?command=view_profile_get"
                                   class="navbar-item"><fmt:message key="view"
                                                                    bundle="${headerBundle}"/>
                                </a>
                            </div>
                        </div>

                    </c:if>
                </div>
            </div>
            <div class="navbar-end">
                <a href="${pageContext.request.contextPath}/controller?command=changing_language_post&language=en"
                   class="navbar-item">en</a>
                <a href="${pageContext.request.contextPath}/controller?command=changing_language_post&language=ru"
                   class="navbar-item">ru</a>
                <c:if test="${not empty sumCertificates}">
                        <span class=" navbar-item has-background-success">
                            <fmt:message key="sum" bundle="${headerBundle}"/>${sumCertificates}
                        </span>
                </c:if>
            </div>
        </div>
    </nav>

    <div class="container is-fluid">
        <router-view/>

    </div>

</div>
