<?php
session_start();

if (isset($_SESSION["idCliente"])) {
    header("Location: home.php");
    die();
} elseif (isset($_SESSION["idAdmin"])) {
    header("Location: admin.php");
    die();
}

?>


<html lang="en">
<head>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" type="text/css" href="stileRegistrazione.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js"></script>
    <script>
        function chkUserAndPass() {
            //Maggiore di 4 caratteri e conferma password rinserimento

            var user = document.getElementById("user").value;
            var pass = document.getElementById("pass").value;
            var passR = document.getElementById("passR").value;
            var err = "";

            if (pass.length < 4)
                err += "La password deve avere una lunghezza maggiore di 4 caratteri<br>";

            if (pass != passR)
                err += "Le password non corrispondono<br>";

            if (err == "") return true;

            window.location = "registrati.php?err=" + err;
            return false;
        }

        function login() {
            $.ajax({
                url: "http://localhost:8080/ProggyWebServices/api/users?username=" + $("#username").val() + "&password=" + $("#password").val(), //Your api url
                type: 'GET', //type is any HTTP method
                xhrFields: {
                    withCredentials: true
                },
                async: false
            }).done((data) => {
                console.log(data);
                //location.reload();
            }).fail((jqXHR, textStatus, errorThrown) => {
                console.error(errorThrown);
                alert("Autenticazione tramite WebService non riuscita. Verrà tentata tramite php, ma non sarà possibile utilizzare le funzioni che richiedono il WebService");
            });
            return true;
        }
    </script>
</head>

<!--  required errored        form-control input py-1 is-autocheck-errored -->
<body class="logged-out env-production page-responsive intent-mouse">
<div class="position-relative js-header-wrapper ">
    <header id="miaBarra" class="Header-old header-logged-out js-details-container Details position-relative f4 py-2"
            role="banner" style="background-color:#1074e7;">
        <div class="container-xl d-lg-flex flex-items-center p-responsive">
            <div class="d-flex flex-justify-between flex-items-center">
                <a class="mr-4" href="index.php" aria-label="Homepage"
                   data-ga-click="(Logged out) Header, go to homepage, icon:logo-wordmark">
                    <!-- IMMAGINE LATO SX -->

                    <svg width="32" class="octicon octicon-mark-github text-white" height="32" viewBox="0 0 512 512"
                         xmlns="http://www.w3.org/2000/svg">
                        <path fill="#fff"
                              d="m321.32 83.367c-12.812-33.246-28.918-61.246-47.426-82.715-6.0781-0.42969-12.195-0.65234-18.344-0.65234-6.1523 0-12.27 0.22266-18.344 0.65234-18.512 21.469-34.617 49.465-47.426 82.715-4.1367 10.734-7.8555 21.863-11.156 33.316h153.85c-3.3047-11.453-7.0234-22.582-11.156-33.316z"/>
                        <path fill="#fff"
                              d="m171.1 365.32h168.9c6.457-30.008 10.207-61.766 11.09-94.316h-191.08c0.88281 32.555 4.6328 64.309 11.09 94.316z"/>
                        <path fill="#fff"
                              d="m340 146.68h-168.9c-6.457 30.008-10.207 61.766-11.09 94.316h191.08c-0.88281-32.555-4.6328-64.309-11.09-94.316z"/>
                        <path fill="#fff"
                              d="m189.78 428.63c12.812 33.246 28.918 61.246 47.426 82.715 6.0781 0.42969 12.195 0.65234 18.348 0.65234 6.1484 0 12.266-0.22266 18.344-0.65234 18.508-21.469 34.613-49.465 47.422-82.715 4.1367-10.734 7.8555-21.863 11.156-33.316h-153.85c3.3008 11.453 7.0195 22.582 11.152 33.316z"/>
                        <path fill="#f00"
                              d="m370.59 146.68c6.0508 29.953 9.6602 61.664 10.508 94.316h130c-1.8984-33.09-10.055-65-23.93-94.34-0.14453 0.003906-0.28516 0.023438-0.42969 0.023438z"/>
                        <path fill="#0f0"
                              d="m140.52 365.32c-6.0547-29.953-9.6641-61.664-10.512-94.316h-130c1.8984 33.09 10.055 65 23.93 94.34 0.14453-0.003906 0.28516-0.023438 0.42969-0.023438z"/>
                        <path fill="#f00"
                              d="m363.55 395.32c-11.168 41.645-27.277 78.977-47.363 109.48 45.199-10.93 86.664-34.066 120.38-67.781 12.848-12.848 24.145-26.828 33.832-41.703z"/>
                        <path fill="#0f0"
                              d="m147.55 116.68c11.172-41.645 27.277-78.977 47.363-109.48-45.195 10.93-86.664 34.066-120.38 67.781-12.844 12.848-24.141 26.828-33.828 41.703z"/>
                        <path fill="#0f0"
                              d="m147.55 395.32h-106.85c9.6875 14.875 20.984 28.855 33.828 41.703 33.719 33.715 75.184 56.852 120.38 67.781-20.082-30.508-36.188-67.84-47.359-109.48z"/>
                        <path fill="#f00"
                              d="m363.55 116.68h106.85c-9.6875-14.875-20.98-28.855-33.828-41.703-33.719-33.715-75.184-56.852-120.38-67.781 20.086 30.508 36.195 67.84 47.363 109.48z"/>
                        <path fill="#f00"
                              d="m381.1 271c-0.84766 32.652-4.4531 64.363-10.508 94.316h116.15c0.14453 0 0.28516 0.019532 0.42969 0.023438 13.875-29.34 22.031-61.25 23.93-94.34z"/>
                        <path fill="#0f0"
                              d="m130 241c0.84766-32.652 4.457-64.363 10.512-94.316h-116.16c-0.14453 0-0.28516-0.019532-0.42969-0.023438-13.875 29.34-22.031 61.25-23.93 94.34z"/>
                    </svg>
                </a>

                <div class="d-lg-none css-truncate css-truncate-target width-fit p-2">

                </div>

                <div class="d-flex flex-items-center">
                    <a href="login.php"
                       class="d-inline-block d-lg-none f5 text-white no-underline border border-gray-dark rounded-2 px-2 py-1 mr-3 mr-sm-5">
                        Login
                    </a>
                    <!--
                    Header-old header-logged-out js-details-container Details position-relative f4 py-2
                    Header-old header-logged-out js-details-container Details position-relative f4 py-2 open Details--on -->
                    <!-- BOTTONE HAMBURGER -->
                    <button class="btn-link d-lg-none mt-1 js-details-target" type="button"
                            onClick="document.getElementById('miaBarra').classList.add('Details--on');document.getElementById('miaBarra').classList.add('open');">
                        <svg height="24" class="octicon octicon-three-bars text-white" viewBox="0 0 12 16" version="1.1"
                             width="18" aria-hidden="true">
                            <path fill="#fff" fill-rule="evenodd"
                                  d="M11.41 9H.59C0 9 0 8.59 0 8c0-.59 0-1 .59-1H11.4c.59 0 .59.41.59 1 0 .59 0 1-.59 1h.01zm0-4H.59C0 5 0 4.59 0 4c0-.59 0-1 .59-1H11.4c.59 0 .59.41.59 1 0 .59 0 1-.59 1h.01zM.59 11H11.4c.59 0 .59.41.59 1 0 .59 0 1-.59 1H.59C0 13 0 12.59 0 12c0-.59 0-1 .59-1z"></path>
                        </svg>
                    </button>
                </div>
            </div>

            <!-- ELEMENTI IN MEZZO -->
            <div class="HeaderMenu HeaderMenu--logged-out position-fixed top-0 right-0 bottom-0 height-fit position-lg-relative d-lg-flex flex-justify-between flex-items-center flex-auto">
                <div class="d-flex d-lg-none flex-justify-end border-bottom bg-gray-light p-3">
                    <button class="btn-link js-details-target" type="button"
                            onClick="document.getElementById('miaBarra').classList.remove('Details--on');document.getElementById('miaBarra').classList.remove('open');">
                        <svg height="24" class="octicon octicon-x text-gray" viewBox="0 0 12 16" version="1.1"
                             width="18" aria-hidden="true">
                            <path fill-rule="evenodd"
                                  d="M7.48 8l3.75 3.75-1.48 1.48L6 9.48l-3.75 3.75-1.48-1.48L4.52 8 .77 4.25l1.48-1.48L6 6.52l3.75-3.75 1.48 1.48L7.48 8z"></path>
                        </svg>
                    </button>
                </div>

                <!-- ELEMENTI LATO SX -->
                <nav class="mt-0 px-3 px-lg-0 mb-5 mb-lg-0" aria-label="Global">
                    <ul class="d-lg-flex list-style-none">

                        <li class="d-block d-lg-flex flex-lg-nowrap flex-lg-items-center border-bottom border-lg-bottom-0 mr-0 mr-lg-3 edge-item-fix position-relative flex-wrap flex-justify-between d-flex flex-items-center ">
                            <details class="HeaderMenu-details details-overlay details-reset width-full">
                                <summary
                                        class="HeaderMenu-summary HeaderMenu-link px-0 py-3 border-0 no-wrap d-block d-lg-inline-block">
                                    Sezioni
                                    <svg x="0px" y="0px" viewBox="0 0 14 8" xml:space="preserve" fill="none"
                                         class="icon-chevon-down-mktg position-absolute position-lg-relative">
                                        <path d="M1,1l6.2,6L13,1"></path>
                                    </svg>
                                </summary>
                                <div class="dropdown-menu flex-auto rounded-1 bg-white px-0 mt-0 pb-4 p-lg-4 position-relative position-lg-absolute left-0 left-lg-n4">
                                    <!-- SOTTO ELEMENTO BOLD -->
                                    <a class="py-2 lh-condensed-ultra d-block link-gray-dark no-underline h5 Bump-link--hover">Pagine
                                        del sito</a>
                                    <!-- SOTTO ELEMENTO -->
                                    <ul class="list-style-none f5 pb-3">
                                        <li class="edge-item-fix"><a href="index.php"
                                                                     class="py-2 lh-condensed-ultra d-block link-gray no-underline f5">Home</a>
                                        </li>
                                        <li class="edge-item-fix"><a href="login.php"
                                                                     class="py-2 lh-condensed-ultra d-block link-gray no-underline f5">Login</a>
                                        </li>
                                        <li class="edge-item-fix"><a href="registratiPage.php"
                                                                     class="py-2 lh-condensed-ultra d-block link-gray no-underline f5">Registrazione</a>
                                        </li>
                                    </ul>

                                    <!-- SOTTO ELEMENTI BOLDs -->
                                    <ul class="list-style-none mb-0 border-lg-top pt-lg-3">
                                        <li class="edge-item-fix"><a href="#"
                                                                     class="py-2 lh-condensed-ultra d-block no-underline link-gray-dark no-underline h5 Bump-link--hover">Informazioni<span
                                                        class="Bump-link-symbol float-right text-normal text-gray-light">→</span></a>
                                        </li>
                                    </ul>
                                </div>
                            </details>
                        </li>

                        <li class="border-bottom border-lg-bottom-0 mr-0 mr-lg-3">
                            <a href="index.php"
                               class="HeaderMenu-link no-underline py-3 d-block d-lg-inline-block">Home</a>
                        </li>
                        <li class="border-bottom border-lg-bottom-0 mr-0 mr-lg-3">
                            <a href="#"
                               class="HeaderMenu-link no-underline py-3 d-block d-lg-inline-block">Enterprise</a>
                        </li>


                    </ul>
                </nav>

                <!-- ELEMENTI LATO DX-->
                <div class="d-lg-flex flex-items-center px-3 px-lg-0 text-center text-lg-left">

                    <a href="registratiPage.php" class="HeaderMenu-link no-underline mr-3">
                        Registrati
                    </a>
                    <a href="account.php"
                       class="HeaderMenu-link d-inline-block no-underline border border-gray-dark rounded-1 px-2 py-1">
                        Login
                    </a>
                </div>
            </div>

        </div>
    </header>

</div>


<div class="application-main " data-commit-hovercards-enabled="">
    <main>
        <div class="p-responsive mt-4 mt-md-8">
            <div class="mb-4 mb-md-8 container-md">
                <div class="text-mono text-center text-gray-light text-normal mb-3">Accedi in Personal Safety</div>
                <h1 class="d-none d-md-block mt-0 mb-3 text-center h00-mktg lh-condensed-ultra ">Login</h1>
            </div>
            <div class="container-sm">
                <div class="mb-4">
                    <?php if (isset($_GET["err"]))
                        echo "<h4 style='color:red'>" . $_GET["err"] . "</h4>";
                    ?>
                    <form action="chklogin.php<?php if (isset($_GET["appenaRegistrato"])) {
                        echo("?appenaRegistrato=1");
                    } ?>" method="post" class="setup-form js-signup-form js-octocaptcha-parent"
                          onsubmit="return login()">

                        <dl class=" form-group my-3 required">
                            <dt class="input-label"><label>Username</label></dt>
                            <dd>
                                <input class="form-control input py-1" type="text" name="username" id="username"
                                       value="<?php if (isset($_GET["oldUsername"])) echo $_GET["oldUsername"]; ?>"
                                       required>
                            </dd>
                        </dl>

                        <dl class="form-group my-3 required">
                            <dt class="input-label"><label>Password</label></dt>
                            <dd>
                                <input class="form-control input py-1" type="password" name="password" id="password"
                                       required>
                            </dd>
                        </dl>


                        <br><br>
                        <div class="my-3">
                            <button class="btn-mktg signup-btn  js-octocaptcha-form-submit width-full" type="submit"
                                    height="64px" data-disable-invalid="" data-disable-with="Creating account…"
                                    id="signup_button"
                                    data-ga-click="Signup funnel entrance, click, text: Create account;">
                                Accedi
                            </button>
                        </div>

                    </form>
                </div>
            </div>
        </div>
    </main>

</div>


</body>

</html>