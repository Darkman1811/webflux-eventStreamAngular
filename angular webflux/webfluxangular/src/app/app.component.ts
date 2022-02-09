import {Component, OnInit} from '@angular/core';
import {ServiceService} from "./service.service"
@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit{
  title = 'webfluxangular';
  results:String[]=[];

  constructor(private service:ServiceService) {
  }
  ngOnInit(): void {
  }

  public getEvents($event:any){
    $event.preventDefault();
    console.log('this is from app componant');
    this.service.getEvent().subscribe(value => {
      console.log('event subscribed');
      console.log(value);
    });
    ;
  }


}
