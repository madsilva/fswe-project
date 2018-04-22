# Code for Functional Testing the Election Portal
# Written By : Nabeel Ahmad Khan
# Last Edited : 15th April 2018


# Loading the libraries
import unittest
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys


class Election(unittest.TestCase):

    def setUp(self):
        # Loading the Chrome Web Driver
        self.driver = webdriver.Chrome(executable_path="/Users/nabeel/Documents/Developer/fswe-project/testing/chrome-driver/chromedriver")

    def test_Login(self):
        driver = self.driver
        driver.get("http://localhost:9000/login")

        # Assertion to confirm that title has Python in it
        self.assertIn("Voting", driver.title)

        user = driver.find_element_by_name("username")

        user.clear()
        user.send_keys("nabil@gmail.com"+Keys.TAB+"nabeel9412"+Keys.TAB+Keys.ENTER)

        assert "Voter" in driver.page_source

    def test_Admin_Login(self):
        driver = self.driver
        driver.get("http://localhost:9000/login")

        # Assertion to confirm that title has Python in it
        self.assertIn("Voting", driver.title)

        user = driver.find_element_by_name("username")

        user.clear()
        user.send_keys("nabil@gmail.com" + Keys.TAB + "nabeel94" + Keys.TAB + Keys.ENTER)

        assert "Admin" in driver.page_source


    def tearDown(self):
        self.driver.close()




##### Main Execution Starts Here
if __name__ == "__main__":
    unittest.main()


