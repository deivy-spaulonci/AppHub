export class Validate{
    notValidString(value) {
        return !value || value.trim() === ""
    }
}
