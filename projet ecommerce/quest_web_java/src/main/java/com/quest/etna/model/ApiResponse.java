package com.quest.etna.model;

public class ApiResponse {
		    //private boolean success;
	    private String message;

	    public ApiResponse(String message) {
	       // this.success = success;
	        this.message = message;
	    }

		/*public boolean isSuccess() {
			return success;
		}*/

		/*public void setSuccess(boolean success) {
			this.success = success;
		}*/

		public String getMessage() {
			return message;
		}

		public void setMessage(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return  message ;
		}
		
		
	}


