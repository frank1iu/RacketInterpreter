;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname test) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))

(check-expect (<= 1 2) true)

(check-expect (<= 1 1) true)

(check-expect (<= 2 1) false)

(check-expect (>= 1 2) false)

(check-expect (>= 1 1) true)

(check-expect (>= 2 1) true)

(check-expect (= 1 1) true)

(check-expect (= 1 2) false)

(check-expect (abs 0) 0)

(check-expect (abs -1) 1)

(check-expect (abs 1) 1)

(check-expect (max 1 0) 1)

(check-expect (max 1 1) 1)

(check-expect (max 0 1) 1)

(check-expect (min -1 1) -1)

(check-expect (min 0 0) 0)

(check-expect (min 1 -1) -1)

(check-expect (zero? 1) false)

(check-expect (zero? 0) true)

(check-expect (mod 4 8) 4)

(check-expect (mod 8 4) 0)

(check-expect (mod 12 5) 2)

(check-expect (gcd 12 5) 1)

(check-expect (gcd 12 -4) 4)

(check-expect (gcd 17 34) 17)