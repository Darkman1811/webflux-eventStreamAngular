import { Injectable } from '@angular/core';
import {Observer,Observable}from "rxjs";
@Injectable({
  providedIn: 'root'
})
export class ServiceService {



  public  getEvent(){
  return new Observable<string[]>( (observer:Observer<string[]>)=>{
    const source= new EventSource("http://localhost:8280/transaction/streamEvent2");
    console.log('this is from service');

    source.onmessage=(event=>{
      console.log(event.data);
      observer.next(event.data);
    })

    source.onerror=(ev => {
      ev.stopPropagation();
    })

    }
  )
  }
}
