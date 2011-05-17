-- TODO
-- before i go to göteborg i want to go to abc
concrete LocatorEng of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy,TransItem,WalkItem,NeedWant= {s : Str} ;    
  lin
    GoTo nw it = {s = "I" ++ nw.s ++ "to go to" ++ it.s}  ;
    GoByCarTo nw it = {s = "I" ++ nw.s  ++ "to go by car to" ++ it.s}  ;
    GoByCarFromTo nw i i' = {s = "I" ++ nw.s ++ "to go by car from" ++ i.s ++ "to" ++ i'.s}  ;

    GoToVia nw i i' = {s = "I" ++ nw.s ++ "to go to" ++ i.s ++ "via" ++ i'.s} ;
    GoFromToVia nw i i' i'' = {s = "I" ++ nw.s ++ "to go from" ++ i.s ++ "to" ++ i'.s ++ "via" ++ i''.s} ;

    WalkTo nw it = {s = "I" ++ nw.s ++ "to walk to" ++ it.s} ;

    GoFromTo nw it it' = {s = "I" ++ nw.s ++ "to go from" ++ it.s ++ "to" ++ it'.s} ;
    
    Need = {s = "need"};
    Want = {s = "want"};    
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
