concrete LocatorSwe of Locator = {
  lincat
    Phrase,Adress,StoreType,Answer,AdressTree= {s : Str} ;    
  lin
    GoTo it = {s = "Jag måste gå till" ++ it.s}  ;
    GoFromTo i i' = {s = "Jag måste gå från" ++ i.s ++ "till" ++ i'.s} ;
    FindStore item = {s = "Var är närmaste" ++ item.s} ;

    AdressT i i' = {s = i.s ++ i'.s} ;
    AdressL i = {s = i.s} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking  = {s = "Gående"} ;
    Transportation = {s = "Transport"} ;
}
