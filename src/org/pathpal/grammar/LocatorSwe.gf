concrete LocatorSwe of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy= {s : Str} ;        
  lin
    GoTo it = {s = "Jag vill till" ++ it.s}  ;
    GoByCarTo it = {s = "Jag vill åka bil till" ++ it.s}  ;
    WalkTo it = {s = "Jag vill gå till " ++ it.s} ;

    GoFromTo i i' = {s = "Jag vill från" ++ i.s ++ "till" ++ i'.s} ;
    FindStore item = {s = "Var är närmaste" ++ item.s} ;

    DString = {s = "dummy"} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking  = {s = "Gående"} ;
    Transportation = {s = "Transport"} ;
}
