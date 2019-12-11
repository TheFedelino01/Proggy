<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?sensor=false"></script>
<script type="text/javascript">
    var markers = [
    {
        "title": 'Aksa Beach',
        "lat": '19.1759668',
        "lng": '72.79504659999998',
    },
    {
        "title": 'Juhu Beach',
        "lat": '19.0883595',
        "lng": '72.82652380000002',
        "description": 'Juhu Beach is one of favourite tourist attractions situated in Mumbai.'
    },
    {
        "title": 'Girgaum Beach',
        "lat": '18.9542149',
        "lng": '72.81203529999993',
        "description": 'Girgaum Beach commonly known as just Chaupati is one of the most famous public beaches in Mumbai.'
    },
    {
        "title": 'Jijamata Udyan',
        "lat": '18.979006',
        "lng": '72.83388300000001',
        "description": 'Jijamata Udyan is situated near Byculla station is famous as Mumbai (Bombay) Zoo.'
    },
    {
        "title": 'Sanjay Gandhi National Park',
        "lat": '19.2147067',
        "lng": '72.91062020000004',
        "description": 'Sanjay Gandhi National Park is a large protected area in the northern part of Mumbai city.'
    }
    ];
    window.onload = function () {
        LoadMap();
    };
 
    var map;
    var marker;
    function LoadMap() {
        var mapOptions = {
            center: new google.maps.LatLng(markers[0].lat, markers[0].lng),
            zoom: 10,
            mapTypeId: google.maps.MapTypeId.ROADMAP
        };
        map = new google.maps.Map(document.getElementById("dvMap"), mapOptions);
        SetMarker(0);
    };
    function SetMarker(position) {
        //Remove previous Marker.
        if (marker != null) {
            marker.setMap(null);
        }
 
        //Set Marker on Map.
        var data = markers[position];
        var myLatlng = new google.maps.LatLng(data.lat, data.lng);
        marker = new google.maps.Marker({
            position: myLatlng,
            map: map,
            title: data.title
        });
 
        //Create and open InfoWindow.
        var infoWindow = new google.maps.InfoWindow();
        infoWindow.setContent("<div style = 'width:100%;'>" + data.description + "</div>");
        infoWindow.open(map, marker);
    };
</script>
<div>
    <label for="rbMarker0">
        <input type="radio" id="rbMarker0" name="rbMarker" value="0" onclick="SetMarker(this.value)"
            checked="checked" />Aksa Beach</label><br />
    <label for="rbMarker1">
        <input type="radio" id="rbMarker1" name="rbMarker" value="1" onclick="SetMarker(this.value)" />Juhu
        Beach</label><br />
    <label for="rbMarker2">
        <input type="radio" id="rbMarker2" name="rbMarker" value="2" onclick="SetMarker(this.value)" />Girgaum
        Beach</label><br />
    <label for="rbMarker3">
        <input type="radio" id="rbMarker3" name="rbMarker" value="3" onclick="SetMarker(this.value)" />Jijamata
        Udyan</label><br />
    <label for="rbMarker4">
        <input type="radio" id="rbMarker4" name="rbMarker" value="4" onclick="SetMarker(this.value)" />Sanjay
        Gandhi National Park</label>
</div>
<hr />
<div id="dvMap" style="width: 100%; height: 500px">
</div>