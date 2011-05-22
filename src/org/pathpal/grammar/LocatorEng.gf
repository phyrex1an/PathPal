-- TODO
-- before i go to göteborg i want to go to abc
concrete LocatorEng of Locator = {
  lincat
    Phrase,StoreType,Answer,Dummy,TransItem,ViaDummy,
    WalkItem,NeedWant,GoItem,FromDummy,ToDummy= {s : Str} ;    
  lin
    INeedWantTo i g = {s= "I" ++ i.s ++ "to" ++ g.s};
    AndThen i = {s = "And then" ++ i.s};
    JustGo i = {s = i.s} ;

    GoTo it = {s = "go" ++ it.s}  ;
    GoTo2 it = {s = it.s} ;
    FromTo i i' = {s = i.s ++ i'.s} ;

    GoFrom it = {s=it.s} ;
    GoByCarTo  it = {s = "go by car" ++ it.s}  ;
    GoByCarFromTo i i' = {s = "go by car" ++ i.s ++ i'.s}  ;

    GoToVia i i' = {s = "go" ++ i.s ++ i'.s} ;
    GoFromToVia i i' i'' = {s = "go from" ++ i.s ++ i'.s ++ i''.s} ;

    WalkTo it = {s = "walk to" ++ it.s} ;

    GoFromTo it it' = {s = "go" ++ it.s ++ it'.s} ;
    
    Need = {s = "need"};
    Want = {s = "want"};    
    
    DString = {s = "dummy"} ;

    WalkOrTrans item = {s = item.s} ;
    
    Walking i = {s = i.s} ;
    Transportation i = {s = i.s} ;

    Via i ={s = "via" ++ i.s} ;
    To i = {s = "to" ++ i.s};
    From i ={s = "from" ++ i.s};



    Walk = {s ="Walk"};
    Walk2 = {s ="Walking"};
    Foot = {s = "By Foot"} ;

    Car = {s ="Car"};
    TBy i= {s = "By" ++ i.s};
    Vehicle = {s ="Vehicle"};
}
