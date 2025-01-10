<nav class="navbar navbar-expand-lg navbar-dark bg-dark">
    <div class="container">
        <a class="navbar-brand" href="${pageContext.request.contextPath}/">
            <img src="${pageContext.request.contextPath}/resources/img/logo.png" alt="Logo" height="30" class="d-inline-block align-text-top me-2">
            Billing System
        </a>
        <button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarNav">
            <span class="navbar-toggler-icon"></span>
        </button>
        <div class="collapse navbar-collapse" id="navbarNav">
            <ul class="navbar-nav me-auto">
                <li class="nav-item">
                    <a class="nav-link ${pageContext.request.servletPath == '/products' ? 'active' : ''}"
                       href="${pageContext.request.contextPath}/products">
                        Products
                    </a>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        Sales
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/sales?action=new">
                                New Sales Bill
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/sales">
                                Sales History
                            </a>
                        </li>
                    </ul>
                </li>
                <li class="nav-item dropdown">
                    <a class="nav-link dropdown-toggle" href="#" data-bs-toggle="dropdown">
                        Purchases
                    </a>
                    <ul class="dropdown-menu">
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/purchases?action=new">
                                New Purchase Bill
                            </a>
                        </li>
                        <li>
                            <a class="dropdown-item" href="${pageContext.request.contextPath}/purchases">
                                Purchase History
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
            <div class="d-flex">
                <button class="btn btn-outline-light" type="button" onclick="toggleTheme()">
                    <i class="bi bi-moon-fill"></i>
                </button>
            </div>
        </div>
    </div>
</nav>