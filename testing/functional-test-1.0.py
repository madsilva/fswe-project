# Code for Functional Testing the Election Portal
# Written By : Nabeel Ahmad Khan
# Last Edited : 15th April 2018


# Loading the libraries
import unittest
from selenium import webdriver 
from selenium.webdriver.common.keys import Keys
from selenium.webdriver.support.ui import Select


class Election(unittest.TestCase):

    def setUp(self):
        # Loading the Chrome Web Driver
        self.driver = webdriver.Chrome(executable_path="/Users/nabeel/Documents/Developer/fswe-project/testing/chrome-driver/chromedriver")

    def test_Login(self):
        driver = self.driver
        driver.get("http://localhost:9000/login")

        # Assertion to confirm that title has Voting in it
        self.assertIn("Voting", driver.title)

        user = driver.find_element_by_name("username")

        user.clear()
        user.send_keys("nabil@gmail.com"+Keys.TAB+"nabeel9412"+Keys.TAB+Keys.ENTER)

        assert "Voter" in driver.page_source

    def test_Admin_Login(self):
        driver = self.driver
        driver.get("http://localhost:9000/login")

        # Assertion to confirm that title has Voting in it
        self.assertIn("Voting", driver.title)

        user = driver.find_element_by_name("username")

        user.clear()
        user.send_keys("anas@gmail.com" + Keys.TAB + "qwerty1234" + Keys.TAB + Keys.ENTER)

        assert "Admin" in driver.page_source

    # Make sure that their is no duplicate election ID before executing this test.
    def test_Admin_election_creation(self):
        driver = self.driver
        driver.get("http://localhost:9000/login")

        user = driver.find_element_by_name("username")

        user.clear()
        user.send_keys("anas@gmail.com" + Keys.TAB + "qwerty1234" + Keys.TAB + Keys.ENTER)

        user = driver.find_element_by_id("createelection")
        user.send_keys(Keys.ENTER)

        # user = driver.find_element_by_name("electionType")
        # user.is_selected("PresedentialElection")

        user = Select(driver.find_element_by_name('electionType'))
        #user.select_by_visible_text("PresidentialElection")
        user.select_by_value("PresidentialElection")

        user = Select(driver.find_element_by_name('state'))
        user.select_by_value("IA")

        user = driver.find_element_by_name("electionID")
        user.send_keys("987346")

        user = driver.find_element_by_name("startDate")
        user.send_keys("02/04/2018")

        user = driver.find_element_by_name("endDate")
        user.send_keys("01/05/2018")

        user.send_keys(Keys.TAB)
        user.send_keys(Keys.TAB)

        user.send_keys(Keys.ENTER)

        assert "Upload" in driver.page_source


    # Make sure that the Election ID is created before executing this test
    # Insert the Election ID below in the Select Statement
    def test_Admin_candidate_creation(self):
        driver = self.driver
        driver.get("http://localhost:9000/login")

        user = driver.find_element_by_name("username")

        user.clear()
        user.send_keys("anas@gmail.com" + Keys.TAB + "qwerty1234" + Keys.TAB + Keys.ENTER)

        user = driver.find_element_by_id("createcandidate")
        user.send_keys(Keys.ENTER)

        user = driver.find_element_by_name("firstname")
        user.send_keys("test-firstname")

        user = driver.find_element_by_name("lastname")
        user.send_keys("test-lastname")

        user = Select(driver.find_element_by_name('party'))
        user.select_by_value("democrats")

        user = Select(driver.find_element_by_name('electionID'))
        user.select_by_value("987346")

        user = Select(driver.find_element_by_name('precinct'))
        user.select_by_value("precinct1")

        user = Select(driver.find_element_by_name('position'))
        user.select_by_value("city representative")

        user = driver.find_element_by_id("finalsubmit")
        user.send_keys(Keys.ENTER)

        assert "Candidate Creation" in driver.page_source


    def tearDown(self):
        self.driver.close()




##### Main Execution Starts Here
if __name__ == "__main__":
    unittest.main()


