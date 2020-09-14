import firebase_admin
from firebase_admin import credentials
from firebase_admin import firestore

#Declaring firestore variable to connect to database
cred = credentials.Certificate('./ServiceAccountKey.json')
default_app = firebase_admin.initialize_app(cred)
db = firestore.client()
col_ref = db.collection(u'applications')

#Custom ApplicationsPOJO object class to store data in firestore database
class ApplicationPOJO(object):
    def __init__(self, companyName, jobName, description):
        self.companyName = companyName
        self.jobName = jobName
        self.description = description


    def from_dict(source):
        # [START_EXCLUDE]
        application = ApplicationPOJO(source[u'companyName'], source[u'jobName'], source[u'description'])
        return application
        # [END_EXCLUDE]


    def to_dict(self):
        # [START_EXCLUDE]
        dest = {
            u'companyName': self.companyName,
            u'jobName': self.jobName,
            u'description': self.description
        }
        return dest
        # [END_EXCLUDE]


    def __repr__(self):
        return(
            f'ApplicationPOJO(\
               companyName={self.companyName}, \
               jobName={self.jobName}, \
                description={self.description}, \
            )'
        )



#Method to display menu to user
def displayMenu():
	userMenuInput = int(input("Choose from the menu above\n1. Log new internship application\n2. Display all applications\n3. Quit\n"))
	if userMenuInput==1:
		comNameInput = input("Enter the name of the company: \n")
		jobNameInput = input("Enter the name of the position: \n")
		descriptionInput = input("Enter job description: \n")
		app = ApplicationPOJO(companyName=comNameInput, jobName=jobNameInput, description=descriptionInput)
		db.collection(u'applications').add(app.to_dict())
		displayMenu()
	elif userMenuInput==2:
		docs = db.collection(u'applications').stream()
		for doc in docs:	
		    print(f'{doc.id} => {doc.to_dict()}')
		displayMenu()
	else:
		exit()

displayMenu()
