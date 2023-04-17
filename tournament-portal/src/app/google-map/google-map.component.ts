import {Component, Input, OnInit} from '@angular/core';

declare const google: any;

@Component({
  selector: 'app-google-map',
  templateUrl: './google-map.component.html',
  styleUrls: ['./google-map.component.scss']
})
export class GoogleMapComponent implements OnInit {
  map!: google.maps.Map;
  @Input() address!: string

  ngOnInit(): void {
    const geocoder = new google.maps.Geocoder();
    const mapOptions: google.maps.MapOptions = {
      center: { lat: 37.7749, lng: -122.4194 },
      zoom: 8
    };
    this.map = new google.maps.Map(document.getElementById('map') as HTMLElement, mapOptions);

    geocoder.geocode({ address: this.address }, (results: google.maps.GeocoderResult[], status: google.maps.GeocoderStatus) => {
      if (status === 'OK') {
        const location = results[0].geometry.location;
        const marker = new google.maps.Marker({
          position: location,
          map: this.map,
          title: 'Google'
        });
        this.map.setCenter(location);
      } else {
        alert('Geocode was not successful for the following reason: ' + status);
      }
    });
  }


}
