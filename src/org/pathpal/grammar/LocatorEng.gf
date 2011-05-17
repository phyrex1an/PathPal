concrete LocatorEng of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy,TransItem,WalkItem= {s : Str} ;    
  lin
    GoTo it = {s = "I need to go to" ++ it.s}  ;
    GoByCarTo it = {s = "I want to go by car to" ++ it.s}  ;
    GoByCarFromTo i i' = {s = "I want to go by car from" ++ i.s ++ "to" ++ i'.s}  ;

    GoToVia i i' = {s = "I want to go to" ++ i.s ++ "via" ++ i'.s} ;
    GoFromToVia i i' i'' = {s = "I want to go from" ++ i.s ++ "to" ++ i'.s ++ "via" ++ i''.s} ;

    WalkTo it = {s = "I want to walk to" ++ it.s} ;

    GoFromTo it it' = {s = "I need to go from" ++ it.s ++ "to" ++ it'.s} ;
    FindStore it = {s = "Where is the nearest" ++ it.s ++ "store"} ;
    
    DString = {s = "dummy"} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking i = {s = i.s} ;
    Transportation i = {s = i.s} ;


    Walk = {s ="Walk"};
    Walk2 = {s ="Walking"};
    Foot = {s = "By Foot"} ;

    Car = {s ="Car"};
    TBy i= {s = "By" ++ i.s};
    Vehicle = {s ="Vehicle"};
}
