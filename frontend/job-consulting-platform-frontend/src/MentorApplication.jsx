import React, { useState } from 'react';
import { registerConsultant } from './api';

const MentorApplication = ({ setView }) => {
  const [step, setStep] = useState(1);
  const [formData, setFormData] = useState({
    name: '',
    email: '',
    specialization: ''
  });
  const [loading, setLoading] = useState(false);
  const [success, setSuccess] = useState(false);
  const [error, setError] = useState(null);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData(prev => ({ ...prev, [name]: value }));
  };

  const handleNext = () => {
    // Basic validation
    if (step === 1 && (!formData.name || !formData.email)) {
      alert("Please fill out all fields.");
      return;
    }
    setStep(prev => prev + 1);
  };

  const handleBack = () => {
    setStep(prev => prev - 1);
  };

  const handleSubmit = async (e) => {
    e.preventDefault();
    if (!formData.specialization) {
      alert("Please enter your specialization.");
      return;
    }
    setLoading(true);
    setError(null);
    try {
      await registerConsultant(formData);
      setSuccess(true);
    } catch (err) {
      setError(err.response?.data?.message || err.message || "Failed to submit application");
    } finally {
      setLoading(false);
    }
  };

  if (success) {
    return (
      <div style={{ padding: '60px 40px', maxWidth: '600px', margin: '0 auto', textAlign: 'center', fontFamily: 'sans-serif', backgroundColor: '#f8f9fa', borderRadius: '8px', marginTop: '40px' }}>
        <h2 style={{ color: '#00b894', marginBottom: '20px' }}>Application Submitted Successfully!</h2>
        <p style={{ color: '#636e72', margin: '20px 0', fontSize: '1.1rem', lineHeight: '1.6' }}>
          Thank you for applying to be a consultant. Our team will review your application and you will hear back shortly regarding the next steps.
        </p>
        <button 
          onClick={() => setView("dashboard")}
          style={{ padding: '12px 24px', backgroundColor: '#0984e3', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', marginTop: '20px', fontSize: '1rem' }}
        >
          Return to Dashboard
        </button>
      </div>
    );
  }

  return (
    <div style={{ padding: '40px', maxWidth: '600px', margin: '0 auto', fontFamily: 'sans-serif' }}>
      <button 
        onClick={() => setView("mentor-benefits")} 
        style={{ marginBottom: '30px', padding: '8px 16px', cursor: 'pointer', border: '1px solid #ccc', borderRadius: '4px', background: '#fff', fontSize: '0.9rem' }}
      >
        ← Back to Benefits
      </button>

      <h2 style={{ color: '#2d3436', marginBottom: '20px', textAlign: 'center' }}>Become a Consultant</h2>
      
      <div style={{ display: 'flex', marginBottom: '40px', borderBottom: '2px solid #dfe6e9', paddingBottom: '15px' }}>
        <div style={{ flex: 1, textAlign: 'center', fontWeight: step === 1 ? '700' : '400', color: step === 1 ? '#0984e3' : '#b2bec3', borderBottom: step === 1 ? '3px solid #0984e3' : 'none', paddingBottom: '10px', transition: 'all 0.3s' }}>
          Step 1: About You
        </div>
        <div style={{ flex: 1, textAlign: 'center', fontWeight: step === 2 ? '700' : '400', color: step === 2 ? '#0984e3' : '#b2bec3', borderBottom: step === 2 ? '3px solid #0984e3' : 'none', paddingBottom: '10px', transition: 'all 0.3s' }}>
          Step 2: Experience
        </div>
      </div>

      {error && (
        <div style={{ padding: '15px', backgroundColor: '#fab1a0', color: '#2d3436', borderRadius: '4px', marginBottom: '20px' }}>
          {error}
        </div>
      )}

      <form onSubmit={step === 2 ? handleSubmit : (e) => { e.preventDefault(); handleNext(); }} style={{ display: 'flex', flexDirection: 'column', gap: '20px' }}>
        
        {step === 1 && (
          <>
            <label style={{ display: 'flex', flexDirection: 'column', gap: '8px', color: '#2d3436', fontWeight: '500' }}>
              Full Name
              <input 
                type="text" 
                name="name" 
                value={formData.name} 
                onChange={handleChange} 
                required 
                placeholder="e.g. John Doe"
                style={{ padding: '12px', borderRadius: '4px', border: '1px solid #b2bec3', fontSize: '1rem' }}
              />
            </label>
            <label style={{ display: 'flex', flexDirection: 'column', gap: '8px', color: '#2d3436', fontWeight: '500' }}>
              Email Address
              <input 
                type="email" 
                name="email" 
                value={formData.email} 
                onChange={handleChange} 
                required 
                placeholder="john@example.com"
                style={{ padding: '12px', borderRadius: '4px', border: '1px solid #b2bec3', fontSize: '1rem' }}
              />
            </label>
          </>
        )}

        {step === 2 && (
          <>
            <label style={{ display: 'flex', flexDirection: 'column', gap: '8px', color: '#2d3436', fontWeight: '500' }}>
              Area of Specialization
              <input 
                type="text" 
                name="specialization" 
                value={formData.specialization} 
                onChange={handleChange} 
                required 
                placeholder="e.g. Software Engineering, Career Coaching..."
                style={{ padding: '12px', borderRadius: '4px', border: '1px solid #b2bec3', fontSize: '1rem' }}
              />
            </label>
            <p style={{ fontSize: '0.85rem', color: '#636e72', marginTop: '-10px' }}>
              Specify the primary area you'd like to consult in. You can add more later.
            </p>
          </>
        )}

        <div style={{ display: 'flex', justifyContent: 'space-between', marginTop: '20px' }}>
          {step > 1 ? (
            <button type="button" onClick={handleBack} style={{ padding: '12px 24px', backgroundColor: '#dfe6e9', color: '#2d3436', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '1rem' }}>
              Previous
            </button>
          ) : (
            <div /> // Spacer
          )}
          
          {step === 1 ? (
            <button type="submit" style={{ padding: '12px 24px', backgroundColor: '#0984e3', color: 'white', border: 'none', borderRadius: '4px', cursor: 'pointer', fontSize: '1rem' }}>
              Next Step
            </button>
          ) : (
            <button type="submit" disabled={loading} style={{ padding: '12px 24px', backgroundColor: '#00b894', color: 'white', border: 'none', borderRadius: '4px', cursor: loading ? 'not-allowed' : 'pointer', fontSize: '1rem', opacity: loading ? 0.7 : 1 }}>
              {loading ? 'Submitting...' : 'Submit Application'}
            </button>
          )}
        </div>
      </form>
    </div>
  );
};

export default MentorApplication;
