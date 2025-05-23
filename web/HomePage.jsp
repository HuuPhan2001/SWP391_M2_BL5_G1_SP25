<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
    <jsp:include page="Header.jsp" />

    <!--header-->

    <div class="banner">
        <div class="container">
            <script src="assets/js/responsiveslides.min.js"></script>
            <script>
                $(function () {
                    $("#slider").responsiveSlides({
                        auto: true,
                        nav: true,
                        speed: 500,
                        namespace: "callbacks",
                        pager: true,
                    });
                });
            </script>
            <div  id="top" class="callbacks_container">
                <ul class="rslides" id="slider">
                    <li>

                        <div class="banner-text">
                            <h3>Lorem Ipsum is not simply dummy  </h3>
                            <p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor .</p>
                            <a href="#">Learn More</a>
                        </div>

                    </li>
                    <li>

                        <div class="banner-text">
                            <h3>There are many variations </h3>
                            <p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor .</p>
                            <a href="#">Learn More</a>

                        </div>

                    </li>
                    <li>
                        <div class="banner-text">
                            <h3>Sed ut perspiciatis unde omnis</h3>
                            <p>Contrary to popular belief, Lorem Ipsum is not simply random text. It has roots in a piece of classical Latin literature from 45 BC, making it over 2000 years old. Richard McClintock, a Latin professor .</p>
                            <a href="#">Learn More</a>

                        </div>

                    </li>
                </ul>
            </div>

        </div>
    </div>

    <!--content-->
    <div class="content">
        <div class="container">
            <div class="content-top">
                <h1>NEW RELEASED</h1>
                <div class="grid-in row">
                    <div class="col-md-4 grid-top">
                        <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/pi.jpg" alt="">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-left    b-delay03 ">
                                    <span>T-Shirt</span>	
                                </h3>
                            </div>
                        </a>


                        <p><a href="#">Contrary to popular</a></p>
                    </div>
                    <div class="col-md-4 grid-top">
                        <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/pi1.jpg" alt="">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-left    b-delay03 ">
                                    <span>Shoe</span>	
                                </h3>
                            </div>
                        </a>
                        <p><a href="#">classical Latin</a></p>
                    </div>
                    <div class="col-md-4 grid-top">
                        <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/pi2.jpg" alt="">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-left    b-delay03 ">
                                    <span>Bag</span>	
                                </h3>
                            </div>
                        </a>
                        <p><a href="#">undoubtable</a></p>
                    </div>
                    <div class="clearfix"> </div>
                </div>
                <div class="grid-in row">
                    <div class="col-md-4 grid-top">
                        <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/pi3.jpg" alt="">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-left    b-delay03 ">
                                    <span>Shirt</span>	
                                </h3>
                            </div>
                        </a>
                        <p><a href="#">suffered alteration</a></p>
                    </div>
                    <div class="col-md-4 grid-top">
                        <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/pi4.jpg" alt="">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-left    b-delay03 ">
                                    <span>Bag</span>	
                                </h3>
                            </div>
                        </a>
                        <p><a href="#">Content here</a></p>
                    </div>
                    <div class="col-md-4 grid-top">
                        <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/pi5.jpg" alt="">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-left    b-delay03 ">
                                    <span>Shoe</span>	
                                </h3>
                            </div>
                        </a>
                        <p><a href="#">readable content</a></p>
                    </div>
                    <div class="clearfix"> </div>
                </div>
            </div>
            <!----->

            <div class="content-top-bottom row">
                <h2>Featured Collections</h2>
                <div class="col-md-6 men">
                    <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/t1.jpg" alt="" style="width: 100%">
                        <div class="b-wrapper">
                            <h3 class="b-animate b-from-top top-in   b-delay03 " style="height: 100%">
                                <span>Lorem</span>	
                            </h3>
                        </div>
                    </a>


                </div>
                <div class="col-md-6">
                    <div class="col-md1 ">
                        <a href="#" class="b-link-stripe b-animate-go thickbox"><img class="img-responsive" src="assets/images/t2.jpg" alt="" style="width: 100%">
                            <div class="b-wrapper">
                                <h3 class="b-animate b-from-top top-in1   b-delay03 " style="height: 100%">
                                    <span>Lorem</span>	
                                </h3>
                            </div>
                        </a>

                    </div>
                    <div class="col-md2 row">
                        <div class="col-md-6 men1">
                            <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/t3.jpg" alt="" style="width: 100%">
                                <div class="b-wrapper">
                                    <h3 class="b-animate b-from-top top-in2   b-delay03 " style="height: 100%">
                                        <span>Lorem</span>	
                                    </h3>
                                </div>
                            </a>

                        </div>
                        <div class="col-md-6 men2">
                            <a href="#" class="b-link-stripe b-animate-go  thickbox"><img class="img-responsive" src="assets/images/t4.jpg" alt="" style="width: 100%">
                                <div class="b-wrapper">
                                    <h3 class="b-animate b-from-top top-in2   b-delay03 " style="height: 100%">
                                        <span>Lorem</span>	
                                    </h3>
                                </div>
                            </a>

                        </div>
                        <div class="clearfix"> </div>
                    </div>
                </div>
                <div class="clearfix"> </div>
            </div>
        </div>
        <!---->
        <div class="content-bottom">
            <ul>
                <li><a href="#"><img class="img-responsive" src="assets/images/lo.png" alt=""></a></li>
                <li><a href="#"><img class="img-responsive" src="assets/images/lo1.png" alt=""></a></li>
                <li><a href="#"><img class="img-responsive" src="assets/images/lo2.png" alt=""></a></li>
                <li><a href="#"><img class="img-responsive" src="assets/images/lo3.png" alt=""></a></li>
                <li><a href="#"><img class="img-responsive" src="assets/images/lo4.png" alt=""></a></li>
                <li><a href="#"><img class="img-responsive" src="assets/images/lo5.png" alt=""></a></li>
                <div class="clearfix"> </div>
            </ul>
        </div>
    </div>
    <div class="footer">
        <jsp:include page="Footer.jsp" />
    </div>
</html>
