-- TODO
-- before i go to göteborg i want to go to abc
concrete LocatorEng of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy,TransItem,WalkItem,NeedWant,GoItem= {s : Str} ;    
  lin
    INeedWantTo i g = {s= "I" ++ i.s ++ "to" ++ g.s};
    AndThen i = {s = "And then" ++ i.s};
    GoTo it = {s = "go to" ++ it.s}  ;
    GoByCarTo  it = {s = "go by car to" ++ it.s}  ;
    GoByCarFromTo i i' = {s = "go by car from" ++ i.s ++ "to" ++ i'.s}  ;

    GoToVia i i' = {s = "go to" ++ i.s ++ "via" ++ i'.s} ;
    GoFromToVia i i' i'' = {s = "go from" ++ i.s ++ "to" ++ i'.s ++ "via" ++ i''.s} ;

    WalkTo it = {s = "walk to" ++ it.s} ;

    GoFromTo it it' = {s = "go from" ++ it.s ++ "to" ++ it'.s} ;
    
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
