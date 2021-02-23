import System.IO()
import System.IO
import System.Environment 
import Control.Monad
import System.Exit
import Data.List
import Test.QuickCheck


main :: IO ()
main = do
    hSetBuffering stdin NoBuffering
    hSetBuffering stdout NoBuffering

    args <- getArgs

    -- if the comand line has "-t"
    if length args /= 0 then when ( head args == "-t") $ tests else do

        line <- getLine
        let lineList = words line
        -- if the input is "exit" then the program ends 
        when ( head lineList == "exit") $ exitSuccess
   
        -- takes from the metric the action and his collumn
        let action = take 2 lineList
        let groups =  [x | x <- drop 2 lineList, x /= "groupby" ] 
        mainLoop action groups [[]]



mainLoop :: [String] -> [String] -> [[Float]] -> IO()
mainLoop action groups list = do  
    
    transaction <- getLine
    let transactionList = words transaction
    
    -- if the transaction  is equal to "exit" then the program ends
    when ( head transactionList == "exit") $ exitSuccess

    -- get the important value and the values of the groups and add them to the list 
    let value = read $ transactionList !! (read $ last action)
    let groupsByValues = groupsValues transactionList groups 
    let updatedList = updateLists list value groupsByValues

    -- If the action is equal to "sum"
    when ( head action == "sum") ( do   
        let valueOfMetric = (sum $ getList updatedList groupsByValues )
        putStrLn (show valueOfMetric)
        )
    
    -- If the action is equal to "average"
    when ( head action == "average") ( do
        let valueOfMetric = ( sum (getList updatedList groupsByValues)) / fromIntegral (length (getList updatedList groupsByValues))
        putStrLn (show valueOfMetric)
        )

    -- If the action is equal to "maximum"
    when ( head action == "maximum" ) ( do
        let valueOfMetric = maximum (getList updatedList groupsByValues)
        putStrLn (show valueOfMetric)
        ) 

    mainLoop action groups updatedList        



--                             
groupsValues :: [String] -> [String] -> [Float]
--groupsValues transactionList  groups 
groupsValues _ [] = []
groupsValues content [y] = [ read $ content !! (read y) ]
groupsValues content (y:ys) = groupsValues content [y] ++ groupsValues content ys 


updateLists :: [[Float]] -> Float -> [Float] -> [[Float]]
--updateLists list value groupsByValues
updateLists [] x groups = [groups ++ [x]]
updateLists (y:ys) x [] = (y ++ [x]) : ys
updateLists (y:ys) x groups 
    | take (length groups) y == groups =  ( y ++ [x] ) : ys  
    | otherwise = y : updateLists ys x groups


getList :: [[Float]] -> [Float] -> [Float] 
--getList list groupsByValues
getList [] _ = []
getList [[]] _ = []
getList [x] [] = x
getList (x:xs) groups 
    | take (length groups) x == groups = drop (length groups) x
    | otherwise =  getList xs groups


tests :: IO()
tests = do
    quickCheck prop_updateLists_length
    quickCheck prop_updateLists_same_elems
    quickCheck prop_getValueFromList_same_elems
    exitSuccess

----------------------------------quickcheck-------------------------------


--Verifies if the new list has the same length as the old list plus 1
prop_updateLists_length:: [[Float]] -> Float -> [Float]  -> Bool
prop_updateLists_length xss x xs = size (updateLists xss x xs) == size xss + 1  || size (updateLists xss x xs) == size xss + length xs + 1
    where size = length . concat

--Verifies if the new list has the same elements of the old one, except for the new element added
prop_updateLists_same_elems:: [[Float]] -> Float -> [Float]  -> Bool
prop_updateLists_same_elems xss x xs = (concat (updateLists xss x xs) \\  concat xss ) \\ xs == [x]

-- Verifies if after the use of the function getList the list remains the same
prop_getValueFromList_same_elems:: [[Float]] -> [Float] -> Bool
prop_getValueFromList_same_elems xss xs = getList xss xs  \\ xs == getList xss xs
