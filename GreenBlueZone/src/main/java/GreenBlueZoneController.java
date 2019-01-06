
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.regex.Pattern;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static java.util.Calendar.HOUR;


@Path("/cars")
public class GreenBlueZoneController {

    private ArrayList<Car> cars = new ArrayList<>();

    //  curl -X PUT http://localhost:8080/GreenBlueZone_Web_exploded/cars/green/AE3456HF

    @PUT
    @Path("/{zone}/{carReg}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response updateCar(@PathParam("zone") String zone, @PathParam("carReg") String carReg) throws ParseException {

        Car wantedCar = findCarByRegNumber(carReg);
        Date currentDate = dateFormatter(new Date());
        Date newDue = dateFormatter(new Date (currentDate.getTime() + HOUR));

        if(zone.equals("green") || zone.equals("blue") && Pattern.matches("[A-Z]{2}[0-9]{4}[A-Z]{2}", carReg)) {
            if(wantedCar == null) {
                cars.add(new Car(carReg, true, newDue, currentDate));
            } else {
                if(wantedCar.getDue().compareTo(dateFormatter(new Date())) > 1) {
                    newDue = dateFormatter(new Date(wantedCar.getDue().getTime() + HOUR));
                }
                wantedCar.setActive(true);
                wantedCar.setDue(newDue);
                wantedCar.setLastAction(currentDate);
            }
        } else {
            // 404
        }

        return Response.ok().status(200).build();

    }

    public JSONObject generateJSONResponse(Car car) throws JSONException {

        JSONObject obj = new JSONObject();

        obj.put("carReg", car.getCarReg());
        obj.put("active", car.getActive());
        obj.put("due", car.getDue());
        obj.put("lastAction", car.getLastAction());

        return obj;
    }

    //  curl -X GET http://localhost:8080/zones/cars/AE3456HF

    @GET
    @Path("/{carReg}")
    @Produces(value={MediaType.APPLICATION_JSON})
    public Response getCar(@PathParam("carReg") String carReg) throws JSONException, ParseException {

        Car wantedCar = findCarByRegNumber(carReg);
        if (wantedCar.getDue().compareTo(dateFormatter(new Date())) > 1) {
            wantedCar.setActive(false);
        }
        return Response.status(200).entity(generateJSONResponse(wantedCar)).build();
        // Else 404
    }

    private Car findCarByRegNumber(final String regNum) {
        return cars.stream()
                .filter(car -> regNum.equals(car.getCarReg()))
                .findAny()
                .orElse(null);
    }

    private Date dateFormatter(Date date) throws ParseException {
        SimpleDateFormat ft = new SimpleDateFormat ("hh:mm:ss zzz dd/MM/yyyy");
        return ft.parse(ft.format(date));
    }

}
