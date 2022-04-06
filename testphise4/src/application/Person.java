package application;
import java.time.LocalDate;

public class Person {

		private  int id;
		private String first_name ; 
		private String last_name ;
		private String address ;
		private String diseases ;
		private String phone_number ;
		private LocalDate birthday ;
		
		
		public Person(int id,String first_name, String last_name, LocalDate birthday, String address, String diseases, String phone_number) {
			super();
			this.id = id;
			this.first_name = first_name;
			this.last_name = last_name;
			this.address = address;
			this.diseases = diseases;
			this.phone_number = phone_number;
			this.birthday = birthday;
		}
		
		
		public Person(String first_name, String last_name, LocalDate birthday, String address, String diseases, String phone_number) {
			super();
			//this.id = id;
			this.first_name = first_name;
			this.last_name = last_name;
			this.address = address;
			this.diseases = diseases;
			this.phone_number = phone_number;
			this.birthday = birthday;
		}
		public int getId() {
			return id;
		}
		public void setId(int id) {
			this.id = id;
		}
		public String getFirstName() {
			return first_name;
		}
		public void setFirstName(String first_name) {
			this.first_name = first_name;
		}
		public String getLastName() {
			return last_name;
		}
		public void setLastName(String last_name) {
			this.last_name = last_name;
		}
		public String getAddress() {
			return address;
		}
		public void setAddress(String address) {
			this.address = address;
		}
		public String getDiseases() {
			return diseases;
		}
		public void setDiseases(String diseases) {
			this.diseases = diseases;
		}
		public String getPhoneNumber() {
			return phone_number;
		}
		public void setPhoneNumber(String phone_number) {
			this.phone_number = phone_number;
		}
		public LocalDate getBirthday() {
			return birthday;
		}
		public void setBirthday(LocalDate string) {
			this.birthday = string;
		}
		@Override
		public String toString() {
			return "person [id=" + id + ", first_name=" + first_name + ", last_name=" + last_name + ", address=" + address
					+ ", diseases=" + diseases + ", phone_number=" + phone_number + ", birthday=" + birthday + "]";
		}
	}



