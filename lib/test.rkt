;; The first three lines of this file were inserted by DrRacket. They record metadata
;; about the language level of this file in a form that our tools can easily process.
#reader(lib "htdp-advanced-reader.ss" "lang")((modname test) (read-case-sensitive #t) (teachpacks ()) (htdp-settings #(#t constructor repeating-decimal #t #t none #f () #t)))

(check-expect (not true) false)

(check-expect (not false) true)

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