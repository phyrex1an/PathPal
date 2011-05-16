concrete LocatorSwe of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy= {s : Str} ;        
  lin
    GoTo it = {s = "Jag måste gå till" ++ it.s}  ;
    GoFromTo i i' = {s = "Jag måste gå från" ++ i.s ++ "till" ++ i'.s} ;
    FindStore item = {s = "Var är närmaste" ++ item.s} ;

    DString = {s = "dummy"} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking  = {s = "Gående"} ;
    Transportation = {s = "Transport"} ;
}
