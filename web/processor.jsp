<%-- 
    Document   : processor
    Created on : May 4, 2015, 5:33:50 PM
    Author     : Calvin He
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Image Processor</title>
        <script type="text/javascript" src="js/fabric-1.5.0.min.js"></script>
        <style type="text/css">
            .canvas
            {
                border-style: solid;
                border-width: 1px;
            }
            .hidden
            {
                display: none;
            }
        </style>
    </head>
    <body>
        <input id="imagePath" value="${requestScope["imgPath"]}" type="hidden"/>
        <div id="nocanvas" class="hidden">
            <p>
                Canvas is not supported by your browser.</p>
            <p>
                Go to <a href="http://caniuse.com/canvas">http://caniuse.com/canvas</a> to see which
                browsers support Canvas.</p>
        </div>
        <canvas id="mainCanvas" class="canvas"></canvas><br>
        <form name="selectionForm" action="SetSelectionServlet" method="POST">
            Left: <input type="text" id="left" readonly><br>
            Top <input type="text" id="top" readonly><br>
            Width <input type="text" id="width" readonly><br>
            Height <input type="text" id="height" readonly><br>
        </form>
        <input type="button" value="Set" onclick="setSelection();"/>
        <script type="text/javascript">
            function setSelection(){
                var url = "SetSelectionServlet";
                req = initRequest();
                req.open("POST", url, true);
                req.setRequestHeader("Content-Type", "application/json;charset=UTF-8");
                req.onreadystatechange = function(){
                    if (req.readyState === 4 && req.status === 200)
                    {
                        alert(req.responseText);
                    }
                };
                var data = {
                    left:document.getElementById("left").value,
                    top:document.getElementById("top").value,
                    width:document.getElementById("width").value,
                    height:document.getElementById("height").value
                };
                req.send(JSON.stringify(data));
            }
            
            function initRequest() {
                if (window.XMLHttpRequest) {
                    if (navigator.userAgent.indexOf('MSIE') !== -1) {
                        isIE = true;
                    }
                    return new XMLHttpRequest();
                } else if (window.ActiveXObject) {
                    isIE = true;
                    return new ActiveXObject("Microsoft.XMLHTTP");
                }
            }
            function isCanvasSupported() {
                var elem = document.createElement('canvas');
                return !!(elem.getContext && elem.getContext('2d'));
            }
            if (!isCanvasSupported()) {
                document.getElementById('nocanvas').className = '';
                document.getElementById('mainCanvas').className = 'hidden';
            }
            else {
                var imageObj = new Image();
                imageObj.onload = function() {
                    //fabric.Object.prototype.transparentCorners = false;
                    var canvas = new fabric.Canvas('mainCanvas');
                    var rect = new fabric.Rect({
                        fill: 'transparent',
                        width: 100,
                        height: 100,
                        stroke: '#f00',
                        strokeWidth: 2
                    });
                    canvas.setWidth(imageObj.width);
                    canvas.setHeight(imageObj.height);
                    canvas.setBackgroundImage(document.getElementById('imagePath').value, canvas.renderAll.bind(canvas), {
                        originX: 'left',
                        originY: 'top'
                    });
                    
                    canvas.on('object:modified', function (e) {
                        var obj = e.target;
                        if (obj.getLeft() < 0
                                || obj.getTop() < 0
                                || obj.getLeft() + obj.getWidth() > canvas.getWidth()
                                || obj.getTop() + obj.getHeight() > canvas.getHeight()) {
                            if (obj.getAngle() !== obj.originalState.angle) {
                                obj.setAngle(obj.originalState.angle);
                            }
                            else {
                                obj.setTop(obj.originalState.top);
                                obj.setLeft(obj.originalState.left);
                                obj.setScaleX(obj.originalState.scaleX);
                                obj.setScaleY(obj.originalState.scaleY);
                            }
                            obj.setCoords();
                        }
                        document.getElementById('left').value = rect.getBoundingRect().left;
                        document.getElementById('top').value = rect.getBoundingRect().top;
                        document.getElementById('width').value = rect.getBoundingRect().width;
                        document.getElementById('height').value = rect.getBoundingRect().height;
                    });
            
                    canvas.add(rect).setActiveObject(rect);
                    canvas.renderAll();
                };
                imageObj.src = document.getElementById('imagePath').value;
            }
        </script>
    </body>
</html>
