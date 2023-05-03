import {Component, EventEmitter, Input, Output} from '@angular/core';

@Component({
  selector: 'app-bracket',
  templateUrl: './bracket.component.html',
  styleUrls: ['./bracket.component.scss']
})
export class BracketComponent {
  @Input() match: any
  @Output() teamNameClicked: EventEmitter<any> = new EventEmitter<any>();

  public handleClick(name: string): void {
    this.teamNameClicked.emit(name);
  }
}
