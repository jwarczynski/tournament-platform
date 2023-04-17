import { Component, OnInit, ViewChild, ElementRef, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-location-search',
  template: '<div #mapContainer style="height: 400px"></div><input type="text" #searchBar>',
})
export class LocationSearchComponent implements OnInit {
  @ViewChild('mapContainer', { static: true })
  mapContainer!: ElementRef<HTMLDivElement>;

  @ViewChild('searchBar', { static: true })
  searchBar!: ElementRef<HTMLInputElement>;

  @Output() locationSelected = new EventEmitter<{ lat: number; lng: number }>();

  map!: google.maps.Map;
  autocomplete!: google.maps.places.Autocomplete;

  ngOnInit() {
    const mapOptions: google.maps.MapOptions = {
      center: new google.maps.LatLng(37.09024, -95.712891),
      zoom: 4,
      disableDefaultUI: true,
    };

    this.map = new google.maps.Map(this.mapContainer.nativeElement, mapOptions);

    this.autocomplete = new google.maps.places.Autocomplete(this.searchBar.nativeElement);

    this.autocomplete.addListener('place_changed', () => {
      const place = this.autocomplete.getPlace();

      if (!place.geometry || !place.geometry.location) {
        console.log('No location found');
        return;
      }

      const { lat, lng } = place.geometry.location.toJSON();
      this.locationSelected.emit({ lat, lng });
    });
  }
}
