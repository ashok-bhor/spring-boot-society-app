Ideas:

Show only verfied Entries

Charge Schema Management:



Create logins for users with flat numbers:
	When the user logs in, set the flat number and flat type in session
	set session timeouts
	In user profile show: flat type: flat number: UserID 

Extra charges Removal:
	Add two more fields to this, Status, Remark
	if added


Reports:
	Fetch the reports of all the approved payments done into excel flat wise till current date
	Fetch Maintenance reports- Includes Maintenance tables flat wise till current date

Create approval flow:
	Create approval page
	On approval page show list of payments with verified as False
	approve and Reject button for each row
	On Approve: 
		Take the username of approver as approved By, Approval Date, Object, Remark "Approved By Admin"
		Save into approval table
		Update Maintenance tables
	On Reject:
		Capture reject reason, reject By, reject Date, Object, Remark "Rejected By Admin"
		Create entry in Reject tables
			
	If the entry is edited by admin, 
		Reject
			- make a rejection entry for that record as rejected with reason "Marking as rejected, Record Number <> was Edited by ADMIN"
			- Deduct the amount from maintenance tables
		Approve
		- Update the entry as approved in approved table with Remark, "Marking as Approved, Record Number <> was Edited by admin"
			- add the amount in maintenance tables
	
For Users/Admin:
	See rejected entries with reason by flat number

