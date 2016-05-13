

    user> (def fracs [0.13 0.11 0.11 0.14 0.13 0.12 0.18 0.15 0.13])
    #'user/fracs
    user> (def values [100 50 100 100 50 50 100 100 50])
    #'user/values
    user> (reduce + values)
    700
    user> (map * fracs values)
    (13.0 5.5 11.0 14.000000000000002 6.5 6.0 18.0 15.0 6.5)
    user> (reduce + (map * fracs values))
    95.5

