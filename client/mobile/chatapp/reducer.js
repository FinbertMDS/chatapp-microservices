import { AppData } from "./src/core/appData";

export const initialState = {
	user: null,
	notification: null,
	currentRoomId: null,
}

export const actionTypes = {
	SET_USER: "SET_USER",
	SET_NOTIFICATION: "SET_NOTIFICATION",
	SET_CURRENT_ROOM_ID: "SET_CURRENT_ROOM_ID",
}

const reducer = (state, action) => {
	switch (action.type) {
		case actionTypes.SET_USER:
			AppData.user = action.user;
			return {
				...state,
				user: action.user
			}
		case actionTypes.SET_NOTIFICATION:
			return {
				...state,
				notification: action.notification
			}
		case actionTypes.SET_CURRENT_ROOM_ID:
			return {
				...state,
				currentRoomId: action.currentRoomId
			}
		default:
			return state;

	}
}

export default reducer;