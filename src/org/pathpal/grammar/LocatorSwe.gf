concrete LocatorSwe of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy,TransItem,WalkItem,NeedWant= {s : Str} ;    
  lin
    GoTo nw it = {s = "Jag" ++ nw.s ++ "till" ++ it.s}  ;
    GoByCarTo nw it = {s = "Jag" ++ nw.s  ++ "åka bil till" ++ it.s}  ;
    GoByCarFromTo nw i i' = {s = "Jag" ++ nw.s ++ "åka bil från" ++ i.s ++ "till" ++ i'.s}  ;

    GoToVia nw i i' = {s = "Jag" ++ nw.s ++ "till" ++ i.s ++ "via" ++ i'.s} ;
    GoFromToVia nw i i' i'' = {s = "Jag" ++ nw.s ++ "från" ++ i.s ++ "till" ++ i'.s ++ "via" ++ i''.s} ;

    WalkTo nw it = {s = "Jag" ++ nw.s ++ "gå till" ++ it.s} ;

    GoFromTo nw it it' = {s = "Jag" ++ nw.s ++ "gå från" ++ it.s ++ "till" ++ it'.s} ;
    
    Need = {s = "måste"};
    Want = {s = "vill"};    
    
    DString = {s = "dummy"} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking i = {s = i.s} ;
    Transportation i = {s = i.s} ;



    Walk = {s ="Gå"};
    Walk2 = {s ="Gående"};

    Car = {s ="Bill"};
    TBy i= {s = "Med" ++ i.s};
    Vehicle = {s ="Fordon"};
}
